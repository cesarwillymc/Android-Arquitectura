package com.doapps.android.conexionmodule.db.dataStore

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.*
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map


class DataStoreManager(private  val context: Context) {

    val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "weatherAppDataStore")

    fun getIntDataStore(key:String): Flow<Int> {
        val preferenceKey = intPreferencesKey(key)
        return  context.dataStore.data
            .map { preferences ->
                preferences[preferenceKey] ?: 0
            }
    }
    fun getStringDataStore(key:String): Flow<String> {
        val preferenceKey = stringPreferencesKey(key)
        return  context.dataStore.data
            .map { preferences ->
                preferences[preferenceKey] ?: ""
            }
    }
    fun getBooleanDataStore(key:String): Flow<Boolean> {
        val preferenceKey = booleanPreferencesKey(key)
        return  context.dataStore.data
            .map { preferences ->
                preferences[preferenceKey] ?: false
            }
    }
    fun getFloatDataStore(key:String): Flow<Float> {
        val preferenceKey = floatPreferencesKey(key)
        return  context.dataStore.data
            .map { preferences ->
                preferences[preferenceKey] ?: 0f
            }
    }

    //Setter
    suspend fun setIntDataStore(key:String,value:Int) {
        val preferenceKey = intPreferencesKey(key)
        context.dataStore.edit { setting->
            setting[preferenceKey] = value
        }.toMutablePreferences()

    }
    suspend fun setStringDataStore(key:String, value:String){
        val preferenceKey = stringPreferencesKey(key)
        context.dataStore.edit { setting->
            setting[preferenceKey] = value
        }
    }
    suspend fun setBooleanDataStore(key:String, value:Boolean){
        val preferenceKey = booleanPreferencesKey(key)
        context.dataStore.edit { setting->
            setting[preferenceKey] = value
        }
    }
    suspend fun setFloatDataStore(key:String, value:Float){
        val preferenceKey = floatPreferencesKey(key)
        context.dataStore.edit { setting->
            setting[preferenceKey] = value
        }
    }
}