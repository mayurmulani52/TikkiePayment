package com.tikkiePayment.model;
import java.util.Objects;

import org.springframework.validation.annotation.Validated;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * ApiError
 */
@Validated
@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2019-03-29T11:50:37.403+08:00[Asia/Singapore]")
public class ApiError   {
  @JsonProperty("code")
  private String code = null;

  @JsonProperty("status")
  private String status = null;

  @JsonProperty("message")
  private String message = null;

  @JsonProperty("errorId")
  private String errorId = null;
  
  public ApiError(String code, String status, String message, String errorId) {
      this.code = code;
      this.status = status;
      this.message = message;
      this.errorId = errorId;
  }

  public ApiError code(String code) {
    this.code = code;
    return this;
  }

  /**
   * The HTTP error code.
   * @return code
  **/

  public String getCode() {
    return code;
  }

  public void setCode(String code) {
    this.code = code;
  }

  public ApiError status(String status) {
    this.status = status;
    return this;
  }

  /**
   * The HTTP error status
   * @return status
  **/

  public String getStatus() {
    return status;
  }

  public void setStatus(String status) {
    this.status = status;
  }

  public ApiError message(String message) {
    this.message = message;
    return this;
  }

  /**
   * The error message
   * @return message
  **/

  public String getMessage() {
    return message;
  }

  public void setMessage(String message) {
    this.message = message;
  }

  public ApiError errorId(String errorId) {
    this.errorId = errorId;
    return this;
  }

  /**
   * The backend error ID
   * @return errorId
  **/

  public String getErrorId() {
    return errorId;
  }

  public void setErrorId(String errorId) {
    this.errorId = errorId;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    ApiError apiError = (ApiError) o;
    return Objects.equals(this.code, apiError.code) &&
        Objects.equals(this.status, apiError.status) &&
        Objects.equals(this.message, apiError.message) &&
        Objects.equals(this.errorId, apiError.errorId);
  }

  @Override
  public int hashCode() {
    return Objects.hash(code, status, message, errorId);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class ApiError {\n");
    
    sb.append("    code: ").append(toIndentedString(code)).append("\n");
    sb.append("    status: ").append(toIndentedString(status)).append("\n");
    sb.append("    message: ").append(toIndentedString(message)).append("\n");
    sb.append("    errorId: ").append(toIndentedString(errorId)).append("\n");
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
