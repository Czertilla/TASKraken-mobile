package com.example.taskraken.network.api;

import com.example.taskraken.network.schemas.structs.RegistOrgResponse;
import com.example.taskraken.network.schemas.tasks.TaskPreviewPagination;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface StructsApi {
    String prefix = "/struct";

    @POST(prefix+"/regist")
    Call<RegistOrgResponse> registOrganization(
            @Query("name") String name,
            @Query("desctription") String description,
            @Query("gen_dir_name") String genDirName,
            @Query("template") String template,
            @Query("can_create_substructures") String canCreateSubstructures,
            @Query("can_create_subordinates") String canCreateSubordinates,
            @Query("can_send_task") String canSendTask,
            @Query("can_send_report") Boolean canSendReport,
            @Query("can_send_petition") String canSendPetition,
            @Query("can_edit_other_rights") String canEditOtherRights,
            @Query("can_edit_oneself_rights") String canEditOneselfRights
            );
}
