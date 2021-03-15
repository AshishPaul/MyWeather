/*
 * Copyright (c) Ashish , 2020.
 * Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *  http://www.apache.org/licenses/LICENSE-2.0
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */

package com.myweather.android.data.local.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.finbloggers.android.data.local.db.model.*

@Database(
    entities = [Blog::class,
        QnA::class,
        Blogger::class,
        GoodRead::class,
        Note::class,
        BlogCategory::class,
        Rating::class,
        Course::class,
        Lesson::class,
        ReadingHistory::class],
    version = 8
)
@TypeConverters(Converters::class)
abstract class AppDataBase : RoomDatabase() {
    abstract fun blogDao(): BlogDao
    abstract fun qnADao(): QnADao
    abstract fun bloggerDao(): BloggerDao
    abstract fun goodReadsDao(): GoodReadsDao
    abstract fun noteDao(): NoteDao
    abstract fun blogCategoryDao(): BlogCategoryDao
    abstract fun ratingDao(): RatingDao
    abstract fun courseDao(): CourseDao
    abstract fun lessonDao(): LessonDao
    abstract fun readingHistoryDao(): ReadingHistoryDao
}