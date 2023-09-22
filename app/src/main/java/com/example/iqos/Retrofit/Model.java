package com.example.iqos.Retrofit;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class Model {


    //Login
    public class Data {

        @SerializedName("id")
        @Expose
        private Integer id;
        @SerializedName("name")
        @Expose
        private String name;     @SerializedName("city")
        @Expose
        private String city;
        @SerializedName("email")
        @Expose
        private String email;
        @SerializedName("phone")
        @Expose
        private String phone;
        @SerializedName("supervisor_id")
        @Expose
        private String supervisorId;
        @SerializedName("region_id")
        @Expose
        private String regionId;
        @SerializedName("email_verified_at")
        @Expose
        private String emailVerifiedAt;
        @SerializedName("role")
        @Expose
        private String role;
        @SerializedName("user_type")
        @Expose
        private String userType;
        @SerializedName("is_password_updated")
        @Expose
        private String isPasswordUpdated;
        @SerializedName("gender")
        @Expose
        private String gender;
        @SerializedName("device_token")
        @Expose
        private String deviceToken;
        @SerializedName("verification_code")
        @Expose
        private String verificationCode;
        @SerializedName("latitude")
        @Expose
        private String latitude;
        @SerializedName("longitude")
        @Expose
        private String longitude;
        @SerializedName("status")
        @Expose
        private String status;
        @SerializedName("deleted_at")
        @Expose
        private String deletedAt;
        @SerializedName("created_at")
        @Expose
        private String createdAt;
        @SerializedName("updated_at")
        @Expose
        private String updatedAt;
        @SerializedName("day_started")
        @Expose
        private String dayStarted;
        @SerializedName("hyper_care")
        @Expose
        private String hyper_care;

        public String getHyper_care() {
            return hyper_care;
        }

        public void setHyper_care(String hyper_care) {
            this.hyper_care = hyper_care;
        }

        public Integer getId() {
            return id;
        }

        public void setId(Integer id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getCity() {
            return city;
        }

        public void setCity(String city) {
            this.city = city;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public String getPhone() {
            return phone;
        }

        public void setPhone(String phone) {
            this.phone = phone;
        }

        public String getSupervisorId() {
            return supervisorId;
        }

        public void setSupervisorId(String supervisorId) {
            this.supervisorId = supervisorId;
        }

        public String getRegionId() {
            return regionId;
        }

        public void setRegionId(String regionId) {
            this.regionId = regionId;
        }

        public String getEmailVerifiedAt() {
            return emailVerifiedAt;
        }

        public void setEmailVerifiedAt(String emailVerifiedAt) {
            this.emailVerifiedAt = emailVerifiedAt;
        }

        public String getRole() {
            return role;
        }

        public void setRole(String role) {
            this.role = role;
        }

        public String getUserType() {
            return userType;
        }

        public void setUserType(String userType) {
            this.userType = userType;
        }

        public String getIsPasswordUpdated() {
            return isPasswordUpdated;
        }

        public void setIsPasswordUpdated(String isPasswordUpdated) {
            this.isPasswordUpdated = isPasswordUpdated;
        }

        public String getGender() {
            return gender;
        }

        public void setGender(String gender) {
            this.gender = gender;
        }

        public String getDeviceToken() {
            return deviceToken;
        }

        public void setDeviceToken(String deviceToken) {
            this.deviceToken = deviceToken;
        }

        public String getVerificationCode() {
            return verificationCode;
        }

        public void setVerificationCode(String verificationCode) {
            this.verificationCode = verificationCode;
        }

        public String getLatitude() {
            return latitude;
        }

        public void setLatitude(String latitude) {
            this.latitude = latitude;
        }

        public String getLongitude() {
            return longitude;
        }

        public void setLongitude(String longitude) {
            this.longitude = longitude;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public String getDeletedAt() {
            return deletedAt;
        }

        public void setDeletedAt(String deletedAt) {
            this.deletedAt = deletedAt;
        }

        public String getCreatedAt() {
            return createdAt;
        }

        public void setCreatedAt(String createdAt) {
            this.createdAt = createdAt;
        }

        public String getUpdatedAt() {
            return updatedAt;
        }

        public void setUpdatedAt(String updatedAt) {
            this.updatedAt = updatedAt;
        }

        public String getDayStarted() {
            return dayStarted;
        }

        public void setDayStarted(String dayStarted) {
            this.dayStarted = dayStarted;
        }
    }
    public class LoginModel {

        @SerializedName("status")
        @Expose
        private Integer status;
        @SerializedName("message")
        @Expose
        private String message;
        @SerializedName("data")
        @Expose
        private Data data;
        @SerializedName("access_token")
        @Expose
        private String accessToken;

        public Integer getStatus() {
            return status;
        }

        public void setStatus(Integer status) {
            this.status = status;
        }

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }

        public Data getData() {
            return data;
        }

        public void setData(Data data) {
            this.data = data;
        }

        public String getAccessToken() {
            return accessToken;
        }

        public void setAccessToken(String accessToken) {
            this.accessToken = accessToken;
        }

    }
 
    public class DataLead {

        @SerializedName("leads")
        @Expose
        private List<Lead> leads;

        public List<Lead> getLeads() {
            return leads;
        }

        public void setLeads(List<Lead> leads) {
            this.leads = leads;
        }

    } 
    public class GetLeadsModel {

        @SerializedName("status")
        @Expose
        private String status;
        @SerializedName("message")
        @Expose
        private String message;
        @SerializedName("data")
        @Expose
        private DataLead data;

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }

        public DataLead getData() {
            return data;
        }

        public void setData(DataLead data) {
            this.data = data;
        }

    } 
    public class Lead {

        @SerializedName("id")
        @Expose
        private Integer id;
        @SerializedName("assigned_at")
        @Expose
        private String assigned_at;
        @SerializedName("first_name")
        @Expose
        private String firstName;
        @SerializedName("is_appointment")
        @Expose
        private String is_appointment;
        @SerializedName("last_name")
        @Expose
        private String lastName;
        @SerializedName("lead_status")
        @Expose
        private String leadStatus;
        @SerializedName("opening_msg")
        @Expose
        private String openingMsg;
        @SerializedName("call1")
        @Expose
        private String call1;
        @SerializedName("call1_outcome")
        @Expose
        private String call1Outcome;
        @SerializedName("call2")
        @Expose
        private String call2;
        @SerializedName("call2_outcome")
        @Expose
        private String call2Outcome;
        @SerializedName("call3")
        @Expose
        private String call3;
        @SerializedName("call3_outcome")
        @Expose
        private String call3Outcome;
        @SerializedName("call4")
        @Expose
        private String call4;
        @SerializedName("call4_outcome")
        @Expose
        private String call4Outcome;
        @SerializedName("last_action")
        @Expose
        private LastAction lastAction;
        @SerializedName("next_action")
        @Expose
        private NextAction nextAction;

        public String getAssigned_at() {
            return assigned_at;
        }

        public void setAssigned_at(String assigned_at) {
            this.assigned_at = assigned_at;
        }

        public String getIs_appointment() {
            return is_appointment;
        }

        public void setIs_appointment(String is_appointment) {
            this.is_appointment = is_appointment;
        }

        public Integer getId() {
            return id;
        }

        public void setId(Integer id) {
            this.id = id;
        }

        public String getFirstName() {
            return firstName;
        }

        public void setFirstName(String firstName) {
            this.firstName = firstName;
        }

        public String getLastName() {
            return lastName;
        }

        public void setLastName(String lastName) {
            this.lastName = lastName;
        }

        public String getLeadStatus() {
            return leadStatus;
        }

        public void setLeadStatus(String leadStatus) {
            this.leadStatus = leadStatus;
        }

        public String getOpeningMsg() {
            return openingMsg;
        }

        public void setOpeningMsg(String openingMsg) {
            this.openingMsg = openingMsg;
        }

        public String getCall1() {
            return call1;
        }

        public void setCall1(String call1) {
            this.call1 = call1;
        }

        public String getCall1Outcome() {
            return call1Outcome;
        }

        public void setCall1Outcome(String call1Outcome) {
            this.call1Outcome = call1Outcome;
        }

        public String getCall2() {
            return call2;
        }

        public void setCall2(String call2) {
            this.call2 = call2;
        }

        public String getCall2Outcome() {
            return call2Outcome;
        }

        public void setCall2Outcome(String call2Outcome) {
            this.call2Outcome = call2Outcome;
        }

        public String getCall3() {
            return call3;
        }

        public void setCall3(String call3) {
            this.call3 = call3;
        }

        public String getCall3Outcome() {
            return call3Outcome;
        }

        public void setCall3Outcome(String call3Outcome) {
            this.call3Outcome = call3Outcome;
        }

        public String getCall4() {
            return call4;
        }

        public void setCall4(String call4) {
            this.call4 = call4;
        }

        public String getCall4Outcome() {
            return call4Outcome;
        }

        public void setCall4Outcome(String call4Outcome) {
            this.call4Outcome = call4Outcome;
        }

        public LastAction getLastAction() {
            return lastAction;
        }

        public void setLastAction(LastAction lastAction) {
            this.lastAction = lastAction;
        }

        public NextAction getNextAction() {
            return nextAction;
        }

        public void setNextAction(NextAction nextAction) {
            this.nextAction = nextAction;
        }

    }
    public class LastAction {

        @SerializedName("type")
        @Expose
        private String type;
        @SerializedName("time")
        @Expose
        private String time;

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getTime() {
            return time;
        }

        public void setTime(String time) {
            this.time = time;
        }

    }
    public class NextAction {

        @SerializedName("type")
        @Expose
        private String type;
        @SerializedName("time")
        @Expose
        private String time;

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getTime() {
            return time;
        }

        public void setTime(String time) {
            this.time = time;
        }

    }
    //Lead Details

    public class DataLeadDetail {

        @SerializedName("lead")
        @Expose
        private LeadDetails lead;

        public LeadDetails getLead() {
            return lead;
        }

        public void setLead(LeadDetails lead) {
            this.lead = lead;
        }

        @SerializedName("configuration")
        @Expose
        private Configuration configuration;

        public Configuration getConfiguration() {
            return configuration;
        }

        public void setConfiguration(Configuration configuration) {
            this.configuration = configuration;
        }
    }
    public class LeadDetails {

        @SerializedName("id")
        @Expose
        private Integer id;
        @SerializedName("first_name")
        @Expose
        private String firstName;
        @SerializedName("last_name")
        @Expose
        private String lastName;
        @SerializedName("number")
        @Expose
        private String number;
        @SerializedName("email")
        @Expose
        private String email;
        @SerializedName("designation")
        @Expose
        private String designation;
        @SerializedName("company")
        @Expose
        private String company;
        @SerializedName("age_group")
        @Expose
        private String ageGroup;
        @SerializedName("city")
        @Expose
        private String city;
        @SerializedName("nic_format")
        @Expose
        private String nicFormat;
        @SerializedName("cig_brand")
        @Expose
        private String cigBrand;
        @SerializedName("best_time_to_contact")
        @Expose
        private String bestTimeToContact;
        @SerializedName("day_for_contact")
        @Expose
        private String dayForContact;
        @SerializedName("lead_status")
        @Expose
        private String leadStatus;
        @SerializedName("last_update")
        @Expose
        private String lastUpdate;
        @SerializedName("upload_time")
        @Expose
        private String uploadTime;
        @SerializedName("approval_status")
        @Expose
        private String approvalStatus;
        @SerializedName("approval_time")
        @Expose
        private String approvalTime;
        @SerializedName("assigned_by")
        @Expose
        private String assignedBy;
        @SerializedName("assigned_at")
        @Expose
        private String assignedAt;
        @SerializedName("qoach_id")
        @Expose
        private String qoachId;
        @SerializedName("opening_msg")
        @Expose
        private String openingMsg;
        @SerializedName("call1")
        @Expose
        private String call1;
        @SerializedName("call1_outcome")
        @Expose
        private String call1Outcome;
        @SerializedName("call2")
        @Expose
        private String call2;
        @SerializedName("call2_outcome")
        @Expose
        private String call2Outcome;
        @SerializedName("call3")
        @Expose
        private String call3;
        @SerializedName("call3_outcome")
        @Expose
        private String call3Outcome;
        @SerializedName("call4")
        @Expose
        private String call4;
        @SerializedName("call4_outcome")
        @Expose
        private String call4Outcome;
        @SerializedName("appointment_status")
        @Expose
        private String appointmentStatus;
        @SerializedName("appointment_date")
        @Expose
        private String appointmentDate;
        @SerializedName("appointment_time")
        @Expose
        private String appointmentTime;
        @SerializedName("appointment_at")
        @Expose
        private String appointmentAt;
        @SerializedName("appointment_location")
        @Expose
        private String appointmentLocation;
        @SerializedName("pre_meeting_checklist_item1")
        @Expose
        private String preMeetingChecklistItem1;
        @SerializedName("pre_meeting_checklist_item2")
        @Expose
        private String preMeetingChecklistItem2;
        @SerializedName("pre_meeting_checklist_item3")
        @Expose
        private String preMeetingChecklistItem3;
        @SerializedName("pre_meeting_checklist_item4")
        @Expose
        private String preMeetingChecklistItem4;
        @SerializedName("pre_meeting_checklist_item5")
        @Expose
        private String preMeetingChecklistItem5;
        @SerializedName("pre_meeting_checklist_item6")
        @Expose
        private String preMeetingChecklistItem6;
        @SerializedName("start_meeting")
        @Expose
        private String startMeeting;
        @SerializedName("checklist_item1")
        @Expose
        private String checklistItem1;
        @SerializedName("checklist_item2")
        @Expose
        private String checklistItem2;
        @SerializedName("checklist_item3")
        @Expose
        private String checklistItem3;
        @SerializedName("checklist_item4")
        @Expose
        private String checklistItem4;
        @SerializedName("checklist_item5")
        @Expose
        private String checklistItem5;
        @SerializedName("checklist_item6")
        @Expose
        private String checklistItem6;
        @SerializedName("checklist_item7")
        @Expose
        private String checklistItem7;
        @SerializedName("checklist_item8")
        @Expose
        private String checklistItem8;
        @SerializedName("checklist_item9")
        @Expose
        private String checklistItem9;
        @SerializedName("checklist_item10")
        @Expose
        private String checklistItem10;
        @SerializedName("video1")
        @Expose
        private String video1;
        @SerializedName("video2")
        @Expose
        private String video2;
        @SerializedName("video3")
        @Expose
        private String video3;
        @SerializedName("q1")
        @Expose
        private String q1;
        @SerializedName("q2")
        @Expose
        private String q2;
        @SerializedName("q3")
        @Expose
        private String q3;
        @SerializedName("q4")
        @Expose
        private String q4;
        @SerializedName("meeting_outcome")
        @Expose
        private String meetingOutcome;
        @SerializedName("sku_sold_color")
        @Expose
        private String skuSoldColor;
        @SerializedName("sku_sr_no")
        @Expose
        private String skuSrNo;
        @SerializedName("amber")
        @Expose
        private String amber;
        @SerializedName("terq")
        @Expose
        private String terq;
        @SerializedName("existing_device")
        @Expose
        private String existingDevice;
        @SerializedName("existing_device_sr_no")
        @Expose
        private String existingDeviceSrNo;
        @SerializedName("existing_device_image_link")
        @Expose
        private String existingDeviceImageLink;
        @SerializedName("discount_offered")
        @Expose
        private String discountOffered;
        @SerializedName("payment_method")
        @Expose
        private String paymentMethod;
        @SerializedName("total_payment")
        @Expose
        private String totalPayment;
        @SerializedName("created_at")
        @Expose
        private String createdAt;
        @SerializedName("updated_at")
        @Expose
        private String updatedAt;
        @SerializedName("email_message")
        @Expose
        private String email_message;
        @SerializedName("last_action")
        @Expose
        private LastAction lastAction;
        @SerializedName("next_action")
        @Expose
        private NextAction nextAction;
        public String getEmail_message() {
            return email_message;
        }

        public LastAction getLastAction() {
            return lastAction;
        }

        public void setLastAction(LastAction lastAction) {
            this.lastAction = lastAction;
        }

        public NextAction getNextAction() {
            return nextAction;
        }

        public void setNextAction(NextAction nextAction) {
            this.nextAction = nextAction;
        }

        public void setEmail_message(String email_message) {
            this.email_message = email_message;
        }

        public Integer getId() {
            return id;
        }

        public void setId(Integer id) {
            this.id = id;
        }

        public String getFirstName() {
            return firstName;
        }

        public void setFirstName(String firstName) {
            this.firstName = firstName;
        }

        public String getLastName() {
            return lastName;
        }

        public void setLastName(String lastName) {
            this.lastName = lastName;
        }

        public String getNumber() {
            return number;
        }

        public void setNumber(String number) {
            this.number = number;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public String getDesignation() {
            return designation;
        }

        public void setDesignation(String designation) {
            this.designation = designation;
        }

        public String getCompany() {
            return company;
        }

        public void setCompany(String company) {
            this.company = company;
        }

        public String getAgeGroup() {
            return ageGroup;
        }

        public void setAgeGroup(String ageGroup) {
            this.ageGroup = ageGroup;
        }

        public String getCity() {
            return city;
        }

        public void setCity(String city) {
            this.city = city;
        }

        public String getNicFormat() {
            return nicFormat;
        }

        public void setNicFormat(String nicFormat) {
            this.nicFormat = nicFormat;
        }

        public String getCigBrand() {
            return cigBrand;
        }

        public void setCigBrand(String cigBrand) {
            this.cigBrand = cigBrand;
        }

        public String getBestTimeToContact() {
            return bestTimeToContact;
        }

        public void setBestTimeToContact(String bestTimeToContact) {
            this.bestTimeToContact = bestTimeToContact;
        }

        public String getDayForContact() {
            return dayForContact;
        }

        public void setDayForContact(String dayForContact) {
            this.dayForContact = dayForContact;
        }

        public String getLeadStatus() {
            return leadStatus;
        }

        public void setLeadStatus(String leadStatus) {
            this.leadStatus = leadStatus;
        }

        public String getLastUpdate() {
            return lastUpdate;
        }

        public void setLastUpdate(String lastUpdate) {
            this.lastUpdate = lastUpdate;
        }

        public String getUploadTime() {
            return uploadTime;
        }

        public void setUploadTime(String uploadTime) {
            this.uploadTime = uploadTime;
        }

        public String getApprovalStatus() {
            return approvalStatus;
        }

        public void setApprovalStatus(String approvalStatus) {
            this.approvalStatus = approvalStatus;
        }

        public String getApprovalTime() {
            return approvalTime;
        }

        public void setApprovalTime(String approvalTime) {
            this.approvalTime = approvalTime;
        }

        public String getAssignedBy() {
            return assignedBy;
        }

        public void setAssignedBy(String assignedBy) {
            this.assignedBy = assignedBy;
        }

        public String getAssignedAt() {
            return assignedAt;
        }

        public void setAssignedAt(String assignedAt) {
            this.assignedAt = assignedAt;
        }

        public String getQoachId() {
            return qoachId;
        }

        public void setQoachId(String qoachId) {
            this.qoachId = qoachId;
        }

        public String getOpeningMsg() {
            return openingMsg;
        }

        public void setOpeningMsg(String openingMsg) {
            this.openingMsg = openingMsg;
        }

        public String getCall1() {
            return call1;
        }

        public void setCall1(String call1) {
            this.call1 = call1;
        }

        public String getCall1Outcome() {
            return call1Outcome;
        }

        public void setCall1Outcome(String call1Outcome) {
            this.call1Outcome = call1Outcome;
        }

        public String getCall2() {
            return call2;
        }

        public void setCall2(String call2) {
            this.call2 = call2;
        }

        public String getCall2Outcome() {
            return call2Outcome;
        }

        public void setCall2Outcome(String call2Outcome) {
            this.call2Outcome = call2Outcome;
        }

        public String getCall3() {
            return call3;
        }

        public void setCall3(String call3) {
            this.call3 = call3;
        }

        public String getCall3Outcome() {
            return call3Outcome;
        }

        public void setCall3Outcome(String call3Outcome) {
            this.call3Outcome = call3Outcome;
        }

        public String getCall4() {
            return call4;
        }

        public void setCall4(String call4) {
            this.call4 = call4;
        }

        public String getCall4Outcome() {
            return call4Outcome;
        }

        public void setCall4Outcome(String call4Outcome) {
            this.call4Outcome = call4Outcome;
        }

        public String getAppointmentStatus() {
            return appointmentStatus;
        }

        public void setAppointmentStatus(String appointmentStatus) {
            this.appointmentStatus = appointmentStatus;
        }

        public String getAppointmentDate() {
            return appointmentDate;
        }

        public void setAppointmentDate(String appointmentDate) {
            this.appointmentDate = appointmentDate;
        }

        public String getAppointmentTime() {
            return appointmentTime;
        }

        public void setAppointmentTime(String appointmentTime) {
            this.appointmentTime = appointmentTime;
        }

        public String getAppointmentAt() {
            return appointmentAt;
        }

        public void setAppointmentAt(String appointmentAt) {
            this.appointmentAt = appointmentAt;
        }

        public String getAppointmentLocation() {
            return appointmentLocation;
        }

        public void setAppointmentLocation(String appointmentLocation) {
            this.appointmentLocation = appointmentLocation;
        }

        public String getPreMeetingChecklistItem1() {
            return preMeetingChecklistItem1;
        }

        public void setPreMeetingChecklistItem1(String preMeetingChecklistItem1) {
            this.preMeetingChecklistItem1 = preMeetingChecklistItem1;
        }

        public String getPreMeetingChecklistItem2() {
            return preMeetingChecklistItem2;
        }

        public void setPreMeetingChecklistItem2(String preMeetingChecklistItem2) {
            this.preMeetingChecklistItem2 = preMeetingChecklistItem2;
        }

        public String getPreMeetingChecklistItem3() {
            return preMeetingChecklistItem3;
        }

        public void setPreMeetingChecklistItem3(String preMeetingChecklistItem3) {
            this.preMeetingChecklistItem3 = preMeetingChecklistItem3;
        }

        public String getPreMeetingChecklistItem4() {
            return preMeetingChecklistItem4;
        }

        public void setPreMeetingChecklistItem4(String preMeetingChecklistItem4) {
            this.preMeetingChecklistItem4 = preMeetingChecklistItem4;
        }

        public String getPreMeetingChecklistItem5() {
            return preMeetingChecklistItem5;
        }

        public void setPreMeetingChecklistItem5(String preMeetingChecklistItem5) {
            this.preMeetingChecklistItem5 = preMeetingChecklistItem5;
        }

        public String getPreMeetingChecklistItem6() {
            return preMeetingChecklistItem6;
        }

        public void setPreMeetingChecklistItem6(String preMeetingChecklistItem6) {
            this.preMeetingChecklistItem6 = preMeetingChecklistItem6;
        }

        public String getStartMeeting() {
            return startMeeting;
        }

        public void setStartMeeting(String startMeeting) {
            this.startMeeting = startMeeting;
        }

        public String getChecklistItem1() {
            return checklistItem1;
        }

        public void setChecklistItem1(String checklistItem1) {
            this.checklistItem1 = checklistItem1;
        }

        public String getChecklistItem2() {
            return checklistItem2;
        }

        public void setChecklistItem2(String checklistItem2) {
            this.checklistItem2 = checklistItem2;
        }

        public String getChecklistItem3() {
            return checklistItem3;
        }

        public void setChecklistItem3(String checklistItem3) {
            this.checklistItem3 = checklistItem3;
        }

        public String getChecklistItem4() {
            return checklistItem4;
        }

        public void setChecklistItem4(String checklistItem4) {
            this.checklistItem4 = checklistItem4;
        }

        public String getChecklistItem5() {
            return checklistItem5;
        }

        public void setChecklistItem5(String checklistItem5) {
            this.checklistItem5 = checklistItem5;
        }

        public String getChecklistItem6() {
            return checklistItem6;
        }

        public void setChecklistItem6(String checklistItem6) {
            this.checklistItem6 = checklistItem6;
        }

        public String getChecklistItem7() {
            return checklistItem7;
        }

        public void setChecklistItem7(String checklistItem7) {
            this.checklistItem7 = checklistItem7;
        }

        public String getChecklistItem8() {
            return checklistItem8;
        }

        public void setChecklistItem8(String checklistItem8) {
            this.checklistItem8 = checklistItem8;
        }

        public String getChecklistItem9() {
            return checklistItem9;
        }

        public void setChecklistItem9(String checklistItem9) {
            this.checklistItem9 = checklistItem9;
        }

        public String getChecklistItem10() {
            return checklistItem10;
        }

        public void setChecklistItem10(String checklistItem10) {
            this.checklistItem10 = checklistItem10;
        }

        public String getVideo1() {
            return video1;
        }

        public void setVideo1(String video1) {
            this.video1 = video1;
        }

        public String getVideo2() {
            return video2;
        }

        public void setVideo2(String video2) {
            this.video2 = video2;
        }

        public String getVideo3() {
            return video3;
        }

        public void setVideo3(String video3) {
            this.video3 = video3;
        }

        public String getQ1() {
            return q1;
        }

        public void setQ1(String q1) {
            this.q1 = q1;
        }

        public String getQ2() {
            return q2;
        }

        public void setQ2(String q2) {
            this.q2 = q2;
        }

        public String getQ3() {
            return q3;
        }

        public void setQ3(String q3) {
            this.q3 = q3;
        }

        public String getQ4() {
            return q4;
        }

        public void setQ4(String q4) {
            this.q4 = q4;
        }

        public String getMeetingOutcome() {
            return meetingOutcome;
        }

        public void setMeetingOutcome(String meetingOutcome) {
            this.meetingOutcome = meetingOutcome;
        }

        public String getSkuSoldColor() {
            return skuSoldColor;
        }

        public void setSkuSoldColor(String skuSoldColor) {
            this.skuSoldColor = skuSoldColor;
        }

        public String getSkuSrNo() {
            return skuSrNo;
        }

        public void setSkuSrNo(String skuSrNo) {
            this.skuSrNo = skuSrNo;
        }

        public String getAmber() {
            return amber;
        }

        public void setAmber(String amber) {
            this.amber = amber;
        }

        public String getTerq() {
            return terq;
        }

        public void setTerq(String terq) {
            this.terq = terq;
        }

        public String getExistingDevice() {
            return existingDevice;
        }

        public void setExistingDevice(String existingDevice) {
            this.existingDevice = existingDevice;
        }

        public String getExistingDeviceSrNo() {
            return existingDeviceSrNo;
        }

        public void setExistingDeviceSrNo(String existingDeviceSrNo) {
            this.existingDeviceSrNo = existingDeviceSrNo;
        }

        public String getExistingDeviceImageLink() {
            return existingDeviceImageLink;
        }

        public void setExistingDeviceImageLink(String existingDeviceImageLink) {
            this.existingDeviceImageLink = existingDeviceImageLink;
        }

        public String getDiscountOffered() {
            return discountOffered;
        }

        public void setDiscountOffered(String discountOffered) {
            this.discountOffered = discountOffered;
        }

        public String getPaymentMethod() {
            return paymentMethod;
        }

        public void setPaymentMethod(String paymentMethod) {
            this.paymentMethod = paymentMethod;
        }

        public String getTotalPayment() {
            return totalPayment;
        }

        public void setTotalPayment(String totalPayment) {
            this.totalPayment = totalPayment;
        }

        public String getCreatedAt() {
            return createdAt;
        }

        public void setCreatedAt(String createdAt) {
            this.createdAt = createdAt;
        }

        public String getUpdatedAt() {
            return updatedAt;
        }

        public void setUpdatedAt(String updatedAt) {
            this.updatedAt = updatedAt;
        }

    }

    public class LeadDetailsModel {

        @SerializedName("status")
        @Expose
        private String status;
        @SerializedName("message")
        @Expose
        private String message;
        @SerializedName("data")
        @Expose
        private DataLeadDetail data;

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }

        public DataLeadDetail getData() {
            return data;
        }

        public void setData(DataLeadDetail data) {
            this.data = data;
        }

    }
    public class Configuration {

        @SerializedName("open_message")
        @Expose
        private String open_message;
        @SerializedName("time_diff")
        @Expose
        private String time_diff;

        public String getOpen_message() {
            return open_message;
        }

        public void setOpen_message(String open_message) {
            this.open_message = open_message;
        }

        public String getTime_diff() {
            return time_diff;
        }

        public void setTime_diff(String time_diff) {
            this.time_diff = time_diff;
        }
    }


    //Get Appointment


    public class Appointment {

        @SerializedName("id")
        @Expose
        private Integer id;
        @SerializedName("first_name")
        @Expose
        private String firstName;
        @SerializedName("last_name")
        @Expose
        private String lastName;
        @SerializedName("number")
        @Expose
        private String number;
        @SerializedName("email")
        @Expose
        private String email;
        @SerializedName("designation")
        @Expose
        private String designation;
        @SerializedName("company")
        @Expose
        private String company;
        @SerializedName("age_group")
        @Expose
        private String ageGroup;
        @SerializedName("city")
        @Expose
        private String city;
        @SerializedName("appointment_status")
        @Expose
        private String appointmentStatus;
        @SerializedName("appointment_date")
        @Expose
        private String appointmentDate;
        @SerializedName("appointment_time")
        @Expose
        private String appointmentTime;
        @SerializedName("appointment_at")
        @Expose
        private String appointmentAt;
        @SerializedName("appointment_location")
        @Expose
        private String appointmentLocation;
        @SerializedName("pre_meeting")
        @Expose
        private String pre_meeting;
        @SerializedName("meeting_complete")
        @Expose
        private String meeting_complete;
        @SerializedName("appointment_message")
        @Expose
        private String message;

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }

        public String getPre_meeting() {
            return pre_meeting;
        }

        public void setPre_meeting(String pre_meeting) {
            this.pre_meeting = pre_meeting;
        }

        public String getMeeting_complete() {
            return meeting_complete;
        }

        public void setMeeting_complete(String meeting_complete) {
            this.meeting_complete = meeting_complete;
        }

        public Integer getId() {
            return id;
        }

        public void setId(Integer id) {
            this.id = id;
        }

        public String getFirstName() {
            return firstName;
        }

        public void setFirstName(String firstName) {
            this.firstName = firstName;
        }

        public String getLastName() {
            return lastName;
        }

        public void setLastName(String lastName) {
            this.lastName = lastName;
        }

        public String getNumber() {
            return number;
        }

        public void setNumber(String number) {
            this.number = number;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public String getDesignation() {
            return designation;
        }

        public void setDesignation(String designation) {
            this.designation = designation;
        }

        public String getCompany() {
            return company;
        }

        public void setCompany(String company) {
            this.company = company;
        }

        public String getAgeGroup() {
            return ageGroup;
        }

        public void setAgeGroup(String ageGroup) {
            this.ageGroup = ageGroup;
        }

        public String getCity() {
            return city;
        }

        public void setCity(String city) {
            this.city = city;
        }

        public String getAppointmentStatus() {
            return appointmentStatus;
        }

        public void setAppointmentStatus(String appointmentStatus) {
            this.appointmentStatus = appointmentStatus;
        }

        public String getAppointmentDate() {
            return appointmentDate;
        }

        public void setAppointmentDate(String appointmentDate) {
            this.appointmentDate = appointmentDate;
        }

        public String getAppointmentTime() {
            return appointmentTime;
        }

        public void setAppointmentTime(String appointmentTime) {
            this.appointmentTime = appointmentTime;
        }

        public String getAppointmentAt() {
            return appointmentAt;
        }

        public void setAppointmentAt(String appointmentAt) {
            this.appointmentAt = appointmentAt;
        }

        public String getAppointmentLocation() {
            return appointmentLocation;
        }

        public void setAppointmentLocation(String appointmentLocation) {
            this.appointmentLocation = appointmentLocation;
        }

    }
    public class DataAppointment {

        @SerializedName("appointments")
        @Expose
        private List<Appointment> appointments;

        public List<Appointment> getAppointments() {
            return appointments;
        }

        public void setAppointments(List<Appointment> appointments) {
            this.appointments = appointments;
        }

    }
    public class GetAppointmentModel {

        @SerializedName("status")
        @Expose
        private String status;
        @SerializedName("message")
        @Expose
        private String message;
        @SerializedName("data")
        @Expose
        private DataAppointment data;

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }

        public DataAppointment getData() {
            return data;
        }

        public void setData(DataAppointment data) {
            this.data = data;
        }

    }


    //update
    public class GenerealModel {

        @SerializedName("status")
        @Expose
        private String status;
        @SerializedName("message")
        @Expose
        private String message;
        @SerializedName("data")
        @Expose
        private DataAppointment data;

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }

        public DataAppointment getData() {
            return data;
        }

        public void setData(DataAppointment data) {
            this.data = data;
        }

    }
    public class GenereModel {

        @SerializedName("status")
        @Expose
        private String status;
        @SerializedName("message")
        @Expose
        private String message;
        @SerializedName("invoice_pdf")
        @Expose
        private String pdf_invoice;

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }

        public String getPdf_invoice() {
            return pdf_invoice;
        }

        public void setPdf_invoice(String pdf_invoice) {
            this.pdf_invoice = pdf_invoice;
        }
    }


    // book app details


    public class Appoinment {

        @SerializedName("id")
        @Expose
        private Integer id;
        @SerializedName("first_name")
        @Expose
        private String firstName;
        @SerializedName("last_name")
        @Expose
        private String lastName;
        @SerializedName("number")
        @Expose
        private String number;
        @SerializedName("email")
        @Expose
        private String email;
        @SerializedName("designation")
        @Expose
        private String designation;
        @SerializedName("company")
        @Expose
        private String company;
        @SerializedName("age_group")
        @Expose
        private String ageGroup;
        @SerializedName("city")
        @Expose
        private String city;
        @SerializedName("appointment_status")
        @Expose
        private String appointmentStatus;
        @SerializedName("appointment_date")
        @Expose
        private String appointmentDate;
        @SerializedName("appointment_time")
        @Expose
        private String appointmentTime;
        @SerializedName("appointment_at")
        @Expose
        private String appointmentAt;
        @SerializedName("appointment_location")
        @Expose
        private String appointmentLocation;

        public Integer getId() {
            return id;
        }

        public void setId(Integer id) {
            this.id = id;
        }

        public String getFirstName() {
            return firstName;
        }

        public void setFirstName(String firstName) {
            this.firstName = firstName;
        }

        public String getLastName() {
            return lastName;
        }

        public void setLastName(String lastName) {
            this.lastName = lastName;
        }

        public String getNumber() {
            return number;
        }

        public void setNumber(String number) {
            this.number = number;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public String getDesignation() {
            return designation;
        }

        public void setDesignation(String designation) {
            this.designation = designation;
        }

        public String getCompany() {
            return company;
        }

        public void setCompany(String company) {
            this.company = company;
        }

        public String getAgeGroup() {
            return ageGroup;
        }

        public void setAgeGroup(String ageGroup) {
            this.ageGroup = ageGroup;
        }

        public String getCity() {
            return city;
        }

        public void setCity(String city) {
            this.city = city;
        }

        public String getAppointmentStatus() {
            return appointmentStatus;
        }

        public void setAppointmentStatus(String appointmentStatus) {
            this.appointmentStatus = appointmentStatus;
        }

        public String getAppointmentDate() {
            return appointmentDate;
        }

        public void setAppointmentDate(String appointmentDate) {
            this.appointmentDate = appointmentDate;
        }

        public String getAppointmentTime() {
            return appointmentTime;
        }

        public void setAppointmentTime(String appointmentTime) {
            this.appointmentTime = appointmentTime;
        }

        public String getAppointmentAt() {
            return appointmentAt;
        }

        public void setAppointmentAt(String appointmentAt) {
            this.appointmentAt = appointmentAt;
        }

        public String getAppointmentLocation() {
            return appointmentLocation;
        }

        public void setAppointmentLocation(String appointmentLocation) {
            this.appointmentLocation = appointmentLocation;
        }

    }

    public class  AppointmentDetailsModel {

        @SerializedName("status")
        @Expose
        private String status;
        @SerializedName("message")
        @Expose
        private String message;
        @SerializedName("data")
        @Expose
        private DataBookAppDetails data;

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }

        public DataBookAppDetails getData() {
            return data;
        }

        public void setData(DataBookAppDetails data) {
            this.data = data;
        }

    }
    public class DataBookAppDetails {

        @SerializedName("appoinment")
        @Expose
        private Appoinment appoinment;

        public Appoinment getAppoinment() {
            return appoinment;
        }

        public void setAppoinment(Appoinment appoinment) {
            this.appoinment = appoinment;
        }

    }

}
