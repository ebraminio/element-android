/*
 * Copyright (c) 2020 New Vector Ltd
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package im.vector.matrix.android.internal.session.room.membership.admin

import im.vector.matrix.android.internal.network.executeRequest
import im.vector.matrix.android.internal.session.room.RoomAPI
import im.vector.matrix.android.internal.task.Task
import javax.inject.Inject

internal interface MembershipAdminTask : Task<MembershipAdminTask.Params, Unit> {

    enum class Type {
        BAN,
        UNBAN,
        KICK
    }

    data class Params(
            val type: Type,
            val roomId: String,
            val userId: String,
            val reason: String?
    )
}

internal class DefaultMembershipAdminTask @Inject constructor(private val roomAPI: RoomAPI) : MembershipAdminTask {

    override suspend fun execute(params: MembershipAdminTask.Params) {
        val userIdAndReason = UserIdAndReason(params.userId, params.reason)
        executeRequest<Unit>(null) {
            apiCall = when (params.type) {
                MembershipAdminTask.Type.BAN   -> roomAPI.ban(params.roomId, userIdAndReason)
                MembershipAdminTask.Type.UNBAN -> roomAPI.unban(params.roomId, userIdAndReason)
                MembershipAdminTask.Type.KICK  -> roomAPI.kick(params.roomId, userIdAndReason)
            }
        }
    }
}