package com.doapps.android.conexionmodule.db.dataStore

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.*
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map


class DataStoreManager {

    companion object{
        val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "weatherAppDataStore")
        fun Context.getIntDataStore(key:String): Flow<Int> {
            val preferenceKey = intPreferencesKey(key)
            return  dataStore.data
                .map { preferences ->
                    preferences[preferenceKey] ?: 0
                }
        }
        fun Context.getStringDataStore(key:String): Flow<String> {
            val preferenceKey = stringPreferencesKey(key)
            return  dataStore.data
                .map { preferences ->
                    preferences[preferenceKey] ?: ""
                }
        }
        fun Context.getBooleanDataStore(key:String): Flow<Boolean> {
            val preferenceKey = booleanPreferencesKey(key)
            return  dataStore.data
                .map { preferences ->
                    preferences[preferenceKey] ?: false
                }
        }
        fun Context.getFloatDataStore(key:String): Flow<Float> {
            val preferenceKey = floatPreferencesKey(key)
            return  dataStore.data
                .map { preferences ->
                    preferences[preferenceKey] ?: 0f
                }
        }

        //Setter
        suspend fun Context.setIntDataStore(key:String,value:Int) {
            val preferenceKey = intPreferencesKey(key)
             dataStore.edit { setting->
                setting[preferenceKey] = value
            }.toMutablePreferences()

        }
        suspend fun Context.setStringDataStore(key:String, value:String){
            val preferenceKey = stringPreferencesKey(key)
            dataStore.edit { setting->
                setting[preferenceKey] = value
            }
        }
        suspend fun Context.setBooleanDataStore(key:String, value:Boolean){
            val preferenceKey = booleanPreferencesKey(key)
            dataStore.edit { setting->
                setting[preferenceKey] = value
            }
        }
        suspend fun Context.setFloatDataStore(key:String, value:Float){
            val preferenceKey = floatPreferencesKey(key)
            dataStore.edit { setting->
                setting[preferenceKey] = value
            }
        }

    }
}