package com.example.studymate.db.dao
import androidx.room.*
import com.example.studymate.db.entity.StudyPlanEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface StudyPlanDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(plan: StudyPlanEntity)

    @Query("SELECT * FROM study_plan ORDER BY dateMillis ASC, timeMillis ASC")
    fun getAllPlans(): Flow<List<StudyPlanEntity>>

    @Delete
    suspend fun delete(plan: StudyPlanEntity)
}
