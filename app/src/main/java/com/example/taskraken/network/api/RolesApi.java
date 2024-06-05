package com.example.taskraken.network.api;

import com.example.taskraken.network.schemas.roles.RolePreview;
import com.example.taskraken.network.schemas.roles.RolesPreviewPagination;
import com.example.taskraken.network.schemas.structs.RegistOrgResponse;

import java.util.UUID;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface RolesApi {
    String prefix = "/role";


    @GET(prefix+"/my-roles")
    Call<RolesPreviewPagination> myRoles(
            @Query("page") int page,
            @Query("size") int size
    );

    @GET(prefix+"/{role_id}/select")
    Call<Object> selectRole(
            @Path("role_id") UUID role_id
    );

    @POST(prefix + "/create")
    Call<Object> createSubordinate(
            @Query("name") String name,
            @Query("template") String template,
            @Query("can_create_substructures") String canCreateSubstructures,
            @Query("can_create_subordinates") String canCreateSubordinates,
            @Query("can_send_task") String canSendTask,
            @Query("can_send_report") Boolean canSendReport,
            @Query("can_send_petition") String canSendPetition,
            @Query("can_reject_task") String canRejectTask,
            @Query("can_create_project") Boolean canCreateProject,
            @Query("can_edit_other_rights") String canEditOtherRights,
            @Query("can_edit_oneself_rights") Boolean canEditOneselfRights
    );

    @GET(prefix + "/search")
    Call<RolesPreviewPagination> all();
}
