package com.app.byron.xoomcountries.data.db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.app.byron.xoomcountries.data.db.models.DisbursementType

@Dao
interface DisbursementTypeDao {

    @Query("SELECT * from disbursement_type_table")
    fun getDisbursementType(): LiveData<List<DisbursementType>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertDisbursementTypes(disbursement: List<DisbursementType>)
}