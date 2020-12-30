/*
 * Copyright (c) 2020.
 * Davin Alfarizky Putra Basudewa , dbasudewa@gmail.com
 * Educational References Only
 */

package xyz.dvnlabs.penilaiandosen.data

import android.content.Context
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject
import org.koin.core.component.KoinApiExtension
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import org.koin.core.parameter.parametersOf
import xyz.dvnlabs.penilaiandosen.data.entity.*
import xyz.dvnlabs.penilaiandosen.utils.AssetParser
import kotlin.reflect.KClass
import kotlin.reflect.KMutableProperty
import kotlin.reflect.KVisibility
import kotlin.reflect.full.createInstance
import kotlin.reflect.full.isSubtypeOf
import kotlin.reflect.full.memberProperties
import kotlin.reflect.full.starProjectedType

@KoinApiExtension
class DataImporter(context: Context) : KoinComponent {
    private val mainDatabase: MainDatabase by inject { parametersOf(context) }
    private val assetParser: AssetParser by inject { parametersOf(context) }

    suspend fun launch() {
        if (mainDatabase.userDAO().getAllUser().isEmpty()) {
            invokers()
        }
    }

    private fun invokers() {
        println("INVOKING DATA IMPORT:: ")
        GlobalScope.launch {
            getJson(User::class, "data/user.json")?.forEach {
                mainDatabase.userDAO().newUser(it)
            }

            getJson(Dosen::class, "data/dosen.json")?.forEach {
                mainDatabase.dosenDAO().insertDosen(it)
            }

            getJson(Lecturer::class, "data/lecturer.json")?.forEach {
                mainDatabase.lecturerDAO().insertLecturer(it)
            }

            getJson(Courses::class, "data/course.json")?.forEach {
                mainDatabase.courseDAO().insertCourse(it)
            }

            getJson(UserCourse::class, "data/usercourse.json")?.forEach {
                mainDatabase.userCourseDAO().insertUserCourse(it)
            }
            getJson(Question::class, "data/question.json")?.forEach {
                mainDatabase.questionDAO().insertQuestion(it)
            }

        }
    }


    private fun <T : Any> getJson(target: KClass<T>, fileName: String): List<T>? {
        val data = assetParser.getJsonAssets(fileName)
        try {
            data?.let {
                val json = JSONObject(data)
                //println(json.toString(2))
                return attachJsonToObject(target, json.getJSONArray("RECORDS"))
            }
        } catch (e: JSONException) {
            e.printStackTrace()
        }

        return null
    }

    private fun <T : Any> attachJsonToObject(clazz: KClass<T>, array: JSONArray): List<T> {
        val listOfData: ArrayList<T> = ArrayList()
        for (i in 0 until array.length()) {
            val newInstance = clazz.createInstance()
            val data = array.getJSONObject(i)
            newInstance::class.memberProperties
                .filter { it.visibility == KVisibility.PUBLIC }
                .filterIsInstance<KMutableProperty<*>>()
                .forEach {
                    /*println("FIELD NAME: ${it.name}")
                    println("JSON: ${data.get(it.name)} ")*/
                    val setterr: Any? = when {
                        it.returnType.isSubtypeOf(String::class.starProjectedType) -> {
                            data.getString(it.name)
                        }
                        it.returnType.isSubtypeOf(Int::class.starProjectedType) -> {
                            data.getInt(it.name)
                        }
                        else -> {
                            null
                        }
                    }

                    it.setter.call(newInstance, setterr)
                }
            listOfData.add(newInstance)
        }
        return listOfData
    }

}