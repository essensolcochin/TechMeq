package com.essensol.techmeq.Api;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface ApiService {


    @POST("ClientApi/InsertUpdateClient")
    @FormUrlEncoded
    Call<RegisterResponse>Register(@Field("ClientId") int ClientId,
                                   @Field("ClientName") String ClientName,
                                   @Field("ContactPerson") String ContactPerson,
                                   @Field("Address") String Address,
                                   @Field("MobileNo1") String MobileNo1,
                                   @Field("MobileNo2") String MobileNo2,
                                   @Field("PhoneNo") String PhoneNo,
                                   @Field("EmailId") String EmailId,
                                   @Field("CountryId") String CountryId,
                                   @Field("ProductType") String ProductType,
                                   @Field("ProductName") String ProductName,
                                   @Field("MachineID") String MachineID,
                                   @Field("Status") boolean Status);


}
