/*
 * Copyright 2020 The Matrix.org Foundation C.I.C.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.matrix.android.sdk.internal.database.model

import io.realm.RealmList
import io.realm.RealmObject

/**
 * Keep the latest state of edition of a message
 */
internal open class EditAggregatedSummaryEntity(
        var aggregatedContent: String? = null,
        // The list of the eventIDs used to build the summary (might be out of sync if chunked received from message chunk)
        var sourceEvents: RealmList<String> = RealmList(),
        var sourceLocalEchoEvents: RealmList<String> = RealmList(),
        var lastEditTs: Long = 0
) : RealmObject() {

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is EditAggregatedSummaryEntity) return false
        if (aggregatedContent != other.aggregatedContent) return false
        return true
    }

    override fun hashCode(): Int {
        return aggregatedContent?.hashCode() ?: 0
    }

    companion object
}
