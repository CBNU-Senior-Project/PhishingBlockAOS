package com.example.phishingblock.network;



import com.example.phishingblock.network.payload.AcceptInvitationRequest;
import com.example.phishingblock.network.payload.AddReportItemRequest;
import com.example.phishingblock.network.payload.DetailPhishingDataResponse;
import com.example.phishingblock.network.payload.GroupMemberResponse;
import com.example.phishingblock.network.payload.GroupRequest;
import com.example.phishingblock.network.payload.InvitationResponse;
import com.example.phishingblock.network.payload.InviteMemberRequest;
import com.example.phishingblock.network.payload.LoginRequest;
import com.example.phishingblock.network.payload.LoginResponse;
import com.example.phishingblock.network.payload.ReportItemResponse;
import com.example.phishingblock.network.payload.SearchPhishingDataRequest;
import com.example.phishingblock.network.payload.SearchPhishingDataResponse;
import com.example.phishingblock.network.payload.SignUpRequest;
import com.example.phishingblock.network.payload.UserIdResponse;
import com.example.phishingblock.network.payload.UserProfileResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.PATCH;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ApiService {
    @POST("user/api/v1/signup")
    Call<Void> signUp(@Body SignUpRequest request);

    // Email duplicate check API
    @GET("/user/api/v1/users/check")
    Call<Void> checkEmailDuplicate(@Query("email") String email);

    // 로그인 API
    @POST("/user/api/v1/signin")
    Call<LoginResponse> login(@Body LoginRequest loginRequest);

    // 그룹 생성 API
    @POST("/user/api/v1/groups")
    Call<Void> createGroup(@Header("Authorization") String token, @Body GroupRequest groupRequest);

    // 신고 데이터 추가
    @POST("/phish/api/v1/add")
    Call<Void> addReportItem(@Header("Authorization") String token, @Body AddReportItemRequest addreportItemRequest);

    // 신고 데이터 조회 type별
    @GET("/phish/api/v1/data")
    Call<List<ReportItemResponse>> getReportItems(@Query("type") String type);

    // 피싱 데이터 검색 API (리스트 형태로 받음)
    @POST("/phish/api/v1/search/type-and-value")
    Call<List<SearchPhishingDataResponse>> searchPhishingData(@Body SearchPhishingDataRequest searchRequest);

    @POST("/phish/api/v1/detail/search")
    Call<List<DetailPhishingDataResponse>> DetailPhishingData(
            @Query("phishingType") String phishingType,
            @Query("value") String value
    );


    // 전화번호로 회원 ID 조회
    @GET("/user/api/v1/users/phone/{phoneNumber}")
    Call<UserIdResponse> getUserIdByPhoneNumber(@Path("phoneNumber") String phoneNumber);

    // 그룹 초대 메시지 전송 API
    @POST("/user/api/v1/groups/{groupId}/invite")
    Call<Void> inviteMember(
            @Header("Authorization") String authorizationToken,
            @Path("groupId") int groupId,
            @Body InviteMemberRequest inviteMemberRequest
    );

    // 초대장 리스트 조회 API
    @GET("/user/api/v1/groups/invitations/{receive_id}")
    Call<List<InvitationResponse>> getInvitations(
            @Header("Authorization") String authorizationToken,
            @Path("receive_id") long receiveId
    );

    // 초대 수락 및 거절 API
    @PATCH("/user/api/v1/groups/invitations/{invitationId}/status")
    Call<Void> acceptInvitation(
            @Header("Authorization") String token,  // Authorization 토큰
            @Path("invitationId") long invitationId,  // 초대 ID
            @Body AcceptInvitationRequest request  // 상태(ACCEPTED/REJECTED)를 담은 요청 본문
    );

    // 회원 정보 조회 API
    @GET("/user/api/v1/users/profile")
    Call<UserProfileResponse> getUserProfile(@Header("X-Authorization") String authorization);

    // 그룹 ID 조회 API
    @GET("/user/api/v1/groups/creator/{creatorId}/group-ids")
    Call<List<Long>> getGroupIds(@Path("creatorId") long creatorId);

    // 그룹 멤버 조회 API
    @GET("/user/api/v1/groups/group/members")
    Call<List<GroupMemberResponse>> getGroupMembers(@Header("Authorization") String token);

    // 그룹 멤버 삭제 API
    @DELETE("/user/api/v1/groups/{groupId}/members/{memberId}")
    Call<Void> deleteGroupMember(
            @Path("groupId") int groupId,
            @Path("memberId") int memberId,
            @Header("Authorization") String token
    );
}

