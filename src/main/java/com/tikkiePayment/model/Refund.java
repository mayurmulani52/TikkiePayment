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

import java.io.IOException;
import java.util.Objects;

import org.threeten.bp.OffsetDateTime;

import com.google.gson.TypeAdapter;
import com.google.gson.annotations.JsonAdapter;
import com.google.gson.annotations.SerializedName;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;

import io.swagger.v3.oas.annotations.media.Schema;
/**
 * Refund
 */

@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.JavaClientCodegen", date = "2020-10-05T18:33:16.985Z[GMT]")
public class Refund {
  @SerializedName("refundToken")
  private String refundToken = null;

  @SerializedName("amountInCents")
  private Integer amountInCents = null;

  @SerializedName("description")
  private String description = null;

  @SerializedName("referenceId")
  private String referenceId = null;

  @SerializedName("createdDateTime")
  private OffsetDateTime createdDateTime = null;

  /**
   * Status of the refund.  Status Description:   ---   - PENDING     &gt; The refund has been scheduled and will be completed as soon as there is enough money in the account. If there is not enough money in the account on a particular day, the refund will be carried over to the next day, and completed when the amount is available.      &gt; The refund will remain in a scheduled state indefinitely, until the amount is available and the refund is executed.   - PAID     &gt; The refund has been paid out. 
   */
  @JsonAdapter(StatusEnum.Adapter.class)
  public enum StatusEnum {
    PENDING("PENDING"),
    PAID("PAID");

    private String value;

    StatusEnum(String value) {
      this.value = value;
    }
    public String getValue() {
      return value;
    }

    @Override
    public String toString() {
      return String.valueOf(value);
    }
    public static StatusEnum fromValue(String text) {
      for (StatusEnum b : StatusEnum.values()) {
        if (String.valueOf(b.value).equals(text)) {
          return b;
        }
      }
      return null;
    }
    public static class Adapter extends TypeAdapter<StatusEnum> {
      @Override
      public void write(final JsonWriter jsonWriter, final StatusEnum enumeration) throws IOException {
        jsonWriter.value(enumeration.getValue());
      }

      @Override
      public StatusEnum read(final JsonReader jsonReader) throws IOException {
        String value = jsonReader.nextString();
        return StatusEnum.fromValue(String.valueOf(value));
      }
    }
  }  @SerializedName("status")
  private StatusEnum status = null;

  public Refund refundToken(String refundToken) {
    this.refundToken = refundToken;
    return this;
  }

   /**
   * Unique token identifying this refund.
   * @return refundToken
  **/
  @Schema(example = "abcdzr8hnVWTgXXcFRLUMc", description = "Unique token identifying this refund.")
  public String getRefundToken() {
    return refundToken;
  }

  public void setRefundToken(String refundToken) {
    this.refundToken = refundToken;
  }

  public Refund amountInCents(Integer amountInCents) {
    this.amountInCents = amountInCents;
    return this;
  }

   /**
   * Amount of the refund in cents (euros).
   * @return amountInCents
  **/
  @Schema(example = "1000", description = "Amount of the refund in cents (euros).")
  public Integer getAmountInCents() {
    return amountInCents;
  }

  public void setAmountInCents(Integer amountInCents) {
    this.amountInCents = amountInCents;
  }

  public Refund description(String description) {
    this.description = description;
    return this;
  }

   /**
   * Description of the refund.
   * @return description
  **/
  @Schema(example = "Refunded 10.00 for broken product.", description = "Description of the refund.")
  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public Refund referenceId(String referenceId) {
    this.referenceId = referenceId;
    return this;
  }

   /**
   * ID for the reference of the API consumer.
   * @return referenceId
  **/
  @Schema(example = "inv_1815_ref_1", description = "ID for the reference of the API consumer.")
  public String getReferenceId() {
    return referenceId;
  }

  public void setReferenceId(String referenceId) {
    this.referenceId = referenceId;
  }

  public Refund createdDateTime(OffsetDateTime createdDateTime) {
    this.createdDateTime = createdDateTime;
    return this;
  }

   /**
   * Timestamp when the refund was created. Format: YYYY-MM-DD:HH:mm:ss.SSSZ.
   * @return createdDateTime
  **/
  @Schema(example = "2019-09-09T12:34:56Z", description = "Timestamp when the refund was created. Format: YYYY-MM-DD:HH:mm:ss.SSSZ.")
  public OffsetDateTime getCreatedDateTime() {
    return createdDateTime;
  }

  public void setCreatedDateTime(OffsetDateTime createdDateTime) {
    this.createdDateTime = createdDateTime;
  }

  public Refund status(StatusEnum status) {
    this.status = status;
    return this;
  }

   /**
   * Status of the refund.  Status Description:   ---   - PENDING     &gt; The refund has been scheduled and will be completed as soon as there is enough money in the account. If there is not enough money in the account on a particular day, the refund will be carried over to the next day, and completed when the amount is available.      &gt; The refund will remain in a scheduled state indefinitely, until the amount is available and the refund is executed.   - PAID     &gt; The refund has been paid out. 
   * @return status
  **/
  @Schema(example = "PAID", description = "Status of the refund.  Status Description:   ---   - PENDING     > The refund has been scheduled and will be completed as soon as there is enough money in the account. If there is not enough money in the account on a particular day, the refund will be carried over to the next day, and completed when the amount is available.      > The refund will remain in a scheduled state indefinitely, until the amount is available and the refund is executed.   - PAID     > The refund has been paid out. ")
  public StatusEnum getStatus() {
    return status;
  }

  public void setStatus(StatusEnum status) {
    this.status = status;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Refund refund = (Refund) o;
    return Objects.equals(this.refundToken, refund.refundToken) &&
        Objects.equals(this.amountInCents, refund.amountInCents) &&
        Objects.equals(this.description, refund.description) &&
        Objects.equals(this.referenceId, refund.referenceId) &&
        Objects.equals(this.createdDateTime, refund.createdDateTime) &&
        Objects.equals(this.status, refund.status);
  }

  @Override
  public int hashCode() {
    return Objects.hash(refundToken, amountInCents, description, referenceId, createdDateTime, status);
  }


  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class Refund {\n");
    
    sb.append("    refundToken: ").append(toIndentedString(refundToken)).append("\n");
    sb.append("    amountInCents: ").append(toIndentedString(amountInCents)).append("\n");
    sb.append("    description: ").append(toIndentedString(description)).append("\n");
    sb.append("    referenceId: ").append(toIndentedString(referenceId)).append("\n");
    sb.append("    createdDateTime: ").append(toIndentedString(createdDateTime)).append("\n");
    sb.append("    status: ").append(toIndentedString(status)).append("\n");
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
