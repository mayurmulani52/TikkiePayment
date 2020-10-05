/*
 * Tikkie Payment Integration APIs
 * This is an integration APIs with Tikkie Payment gateway
 *
 * OpenAPI spec version: 0.0.1
 * 
 *
 * NOTE: This class is auto generated by the swagger code generator program.
 * https://github.com/swagger-api/swagger-codegen.git
 * Do not edit the class manually.
 */

package com.tikkiepayment.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import com.google.gson.annotations.SerializedName;

import io.swagger.v3.oas.annotations.media.Schema;
/**
 * GetPaymentRequestListSuccess
 */

@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.JavaClientCodegen", date = "2020-10-05T18:33:16.985Z[GMT]")
public class GetPaymentRequestListSuccess {
  @SerializedName("paymentRequests")
  private List<PaymentRequsetResponse> paymentRequests = null;

  @SerializedName("totalElementCount")
  private Integer totalElementCount = null;

  public GetPaymentRequestListSuccess paymentRequests(List<PaymentRequsetResponse> paymentRequests) {
    this.paymentRequests = paymentRequests;
    return this;
  }

  public GetPaymentRequestListSuccess addPaymentRequestsItem(PaymentRequsetResponse paymentRequestsItem) {
    if (this.paymentRequests == null) {
      this.paymentRequests = new ArrayList<PaymentRequsetResponse>();
    }
    this.paymentRequests.add(paymentRequestsItem);
    return this;
  }

   /**
   * Get paymentRequests
   * @return paymentRequests
  **/
  @Schema(description = "")
  public List<PaymentRequsetResponse> getPaymentRequests() {
    return paymentRequests;
  }

  public void setPaymentRequests(List<PaymentRequsetResponse> paymentRequests) {
    this.paymentRequests = paymentRequests;
  }

  public GetPaymentRequestListSuccess totalElementCount(Integer totalElementCount) {
    this.totalElementCount = totalElementCount;
    return this;
  }

   /**
   * Total amount of payments which match the parameters provided.
   * @return totalElementCount
  **/
  @Schema(example = "75", description = "Total amount of payments which match the parameters provided.")
  public Integer getTotalElementCount() {
    return totalElementCount;
  }

  public void setTotalElementCount(Integer totalElementCount) {
    this.totalElementCount = totalElementCount;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    GetPaymentRequestListSuccess getPaymentRequestListSuccess = (GetPaymentRequestListSuccess) o;
    return Objects.equals(this.paymentRequests, getPaymentRequestListSuccess.paymentRequests) &&
        Objects.equals(this.totalElementCount, getPaymentRequestListSuccess.totalElementCount);
  }

  @Override
  public int hashCode() {
    return Objects.hash(paymentRequests, totalElementCount);
  }


  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class GetPaymentRequestListSuccess {\n");
    
    sb.append("    paymentRequests: ").append(toIndentedString(paymentRequests)).append("\n");
    sb.append("    totalElementCount: ").append(toIndentedString(totalElementCount)).append("\n");
    sb.append("}");
    return sb.toString();
  }

  /**
   * Convert the given object to string with each line indented by 4 spaces
   * (except the first line).
   */
  private String toIndentedString(java.lang.Object o) {
    if (o == null) {
      return "null";
    }
    return o.toString().replace("\n", "\n    ");
  }

}