/*
 * Tikkie
 * # Overview The Tikkie API enables Tikkie business customers to create payment requests.  This API provides the following functionality: - Create payment requests and retrieve transaction details - List payments that have been made on a request - Refund payments and retrieve refund details - Subscribe to notifications on payments, refunds, and bundled payouts. - Retrieve transaction bundles. Bundled payout must be enabled.  For more information, see [https://tikkie.me](https://tikkie.me).  ## App token An app token is a unique token per API connection. It is used to identify your company. Your Tikkie account can have multiple app tokens. It is possible to assign different permissions to this token.  >**Important:** In a production environment, `appToken` is created in the [Tikkie Business Portal](https://business.tikkie.me).  `appToken` has three permission types, these are as follows:  | Permission | Description| |---|---| | Payment Request | Create payment requests, retrieve payment request details, execute payments and refunds, and create subscribe to payment request notifications. | | Refund          | Create refunds. Refund is an additional permission on top of Payment Request). | | Transactions    | Retrieve transaction bundles and subscribe to transaction notifications. |  ## Thresholds  Different thresholds apply to aspects of payment requests, such as: the number of payments per payment request, the maximum amount of a payment request, and the maximum yield. These thresholds are discussed on a per customer basis when access to production is requested. These thresholds are not applicable in the sandbox environment.  ## Payout of transactions  By default, your Tikkie transactions are bundled and paid out on your account once a day. To get transactions insights, the application must have transactions permission. For more information, see [App token](#section/Overview/App-token).  For some businesses transactions are paid out individually and directly. In that case, the application doesn't need the transactions functionality.  Bundle information can be retrieved with the [GET Transaction Bundles](#operation/getBundleList) and [GET Transaction Bundle](#operation/getBundle) endpoints.  ### Downloading transaction bundle files  It is possible to download a file that contains all transactions for a specific bundle, which can be used in your bookkeeping system. Currently, we support the CAMT.053 format only.  Files are available for download from the moment that the bundle has been paid out. If you want to get notified when this happens, please set up a transactions subscription using: [POST Transactions Subscription](#operation/subscribeTransactionsNotifications). The full file url can be found in the `files` property of the bundle by executing [GET Bundle](#operation/getBundle). When the url is obtained, perform a GET on the url with the `App token` and `API Key` headers.  # Requirements To use this API in a production environment, you must have the following: - Tikkie contract. [Click here to sign up](https://www.tikkie.me/aanvragen-zakelijk). - An [app token](#section/Overview/App-token) that is created in [Tikkie Business Portal](https://business.tikkie.me). This is not required to use this API in the sandbox.  >**Important:** Consumer to Consumer (C2C) use of the API is prohibited.  >**Important:** Generation of payment requests on behalf of third parties is prohibited.  # Sandbox access  Use the ABN AMRO Developer Sandbox to access dummy data, and safely test all functionality of an API. For most products, the sandbox environment is identical to production.  To gain access to the Tikkie Payment Request API in a sandbox environment, complete the [Tutorial](#section/Tutorial).  # Production access  When you are ready to go live with your application, complete the following steps:  >**Important:** In production, the `appToken`, described in [Step 1](#section/Tutorial/Step-1-Get-an-API-key), is created in the [Tikkie Business Portal](https://business.tikkie.me). The functionality described in this step is available in the sandbox only.  >**Note:** To go live with your application, you must create a new application in the developer portal. A different API key is required for your production environment. This is described below.  1. Login to your account. 1. In the top navigation bar, click **My Apps**. 1. Click **+ Add a new App**. 1. Enter a name for your application, select **Tikkie**, and click **Submit**. 1. Go to the [Contact form](https://developer.abnamro.com/contact). 1. In the **Support Category** field, select **Access to production**. 1. In the **Subject field**, enter the name of your application and request access, in the following format:   `Application name=Tikkie: request for access to production.` 1. In the **Email** field, use the same developer e-mail that you used when setting up your application. 1. In the **Message** field, enter: `Request production access`. 1. Click **Send**. 1. The Tikkie Service Team will contact you to discuss the details of your request. Once approved, you are ready to use this API in a live environment.  # Tutorial  This tutorial describes how to connect the Tikkie API to the sandbox environment, along with the APIs functionality.  You can also: [run this API in Postman](https://app.getpostman.com/run-collection/4d3799a37e2f291c0cc8)  ## Step 1 - Get an API key  1. Register and create an account:   1. Go to <a href=\"https://developer.abnamro.com/\" target=\"_blank\">https://developer.abnamro.com/</a> and click **Sign up**.   1. Enter your details, and click **Create an account**.   1. Developer Support will send you an activation link by email.   1. Click the activation link. 1. Create and register an application:   1. Log in to your account.   1. In the top navigation bar, click **My Apps**.   1. Click **Add a new App** or **+**.   1. In the **App name** field, enter a name for your application.   1. In the **API product** field, select **Tikkie API**, and click **Submit**. 1. Obtain your API key:   1. In the top navigation bar, click **My Apps**.   1. Click on the app you created.   1. Copy the **API Key** number..  ## Step 2 - Create a sandbox appToken  >**Important:** In a production environment, `appToken` is created in the [Tikkie Business Portal](https://business.tikkie.me). The functionality described in this step is available in the sandbox environment only.  To create a new application, which has all rights enabled, using the API key you obtained in the previous step, execute: [POST Sandbox App](#operation/createSandboxApp).  The response contains an `appToken` which is used in subsequent requests.  ## Step 3 - Create a payment request  To create a payment, using the `appToken` you created in Step 2, execute: [POST Payment Request](#operation/createPaymentRequest).  This operation creates a new payment request as specified by the body of the request, and returns a `paymentRequestToken` which is used to access your payment request.  The operation also returns a `url` that is used to direct the user to where the request can be paid.  ## Step 4 - Retrieve payment request details  To retrieve details of payment requests, use one of the following operations:  - [GET Payment Request](#operation/getPaymentRequest): Returns the details of one payment request, specified by a `paymentRequestToken`.  - [GET Payment Requests](#operation/getPaymentRequestList): Returns a list of payment requests based on the query parameters passed in the request. This list is paginated.  ## Step 5 - Retrieve payment details  When a user pays a payment request, the transaction is marked as a 'payment'. This indicates that the payment is completed.  To retrieve payment details, use one of the following operations:  - [GET Payment](#operation/getPayment): Returns the details of one payment, specified by a `paymentToken`.  - [GET Payments](#operation/getPaymentList): Returns a list of payments based on the query parameters passed in the request. This list is paginated.  ## Step 6 - Create a refund  To create a refund, execute: [POST Refund](#operation/createRefund).  This operation initiates a refund on a specific payment, and returns a `refundToken` which can be used to retrieve details.  ## Step 7 - Retrieve refund details  To retrieve refund details, execute: [GET Refund](#operation/getRefund).  This operation returns the details of one refund, specified by a `refundToken`.  ## Step 8 - Set up a payment subscription  To receive notifications when a payment is paid, or when a refund is been paid out, set up a subscription using: [POST Notification Subscription](#operation/subscribePaymentRequestNotifications).  HTTP post requests will be made to the specified `url`, containing information about the payment or refund in the body of the request.  ## Step 9 - To cancel a payment subscription  To cancel a subscription and stop receiving notifications, execute: [DELETE Notification Subscription](#operation/deletePaymentRequestNotifications).  ## Step 10 - Retrieve transaction bundle details  To retrieve details of transactions, use one of the following operations:  - [GET Transaction Bundle](#operation/getBundle): Returns the details of one transaction bundle, specified by a `bundleId`.  - [GET Transaction Bundles](#operation/getBundleList): Returns a list of transaction bundles, based on the query parameters passed in the request. This list is paginated.  - [Download bundle file](<#section/Overview/Payout of transactions/Downloading transaction bundle files>): Downloads a CAMT.053 file of a specific bundle.  ## Step 11 - Set up a transactions subscription  To receive notifications when a transactions bundle is paid, set up a subscription using: [POST Transactions Subscription](#operation/subscribeTransactionsNotifications).  HTTP post requests will be made to the specified `url`, containing information about the transactions bundle in the body of the request.  ## Step 12 - To cancel a transactions subscription  To cancel a subscription and stop receiving notifications, execute: [DELETE Transactions Subscription](#operation/deleteTransactionsNotifications).  # Release notes  ## Version 2.1.2 - Added CAMT.053 file format to transaction bundles.  ## Version 2.1.1 - Removed duplicate Transaction bundle entry.  ## Version 2.1.0 - Added the transaction details module. It is now possible to retrieve bundles payout transaction bundles, and subscribe to notifications when a bundle is available.  Operations added in this release: - GET /transactionbundles - GET /transactionbundles/{bundleId} - POST /transactionssubscription - DELETE /transactionssubscription  ## Version 2.0.1 - Added referenceId format details.  ## Version 2.0.0 - Released this API.
 *
 * OpenAPI spec version: 2.1.2
 * 
 *
 * NOTE: This class is auto generated by the swagger code generator program.
 * https://github.com/swagger-api/swagger-codegen.git
 * Do not edit the class manually.
 */

package com.tikkiepayment.external.tikkie.model;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

import org.threeten.bp.OffsetDateTime;

import com.google.gson.annotations.SerializedName;

import io.swagger.v3.oas.annotations.media.Schema;
/**
 * TransactionBundle
 */

@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.JavaClientCodegen", date = "2020-10-04T13:37:11.595Z[GMT]")
public class TransactionBundle {
  @SerializedName("bundleId")
  private UUID bundleId = null;

  @SerializedName("reference")
  private String reference = null;

  @SerializedName("files")
  private List<File> files = null;

  @SerializedName("startDateTime")
  private OffsetDateTime startDateTime = null;

  @SerializedName("endDateTime")
  private OffsetDateTime endDateTime = null;

  public TransactionBundle bundleId(UUID bundleId) {
    this.bundleId = bundleId;
    return this;
  }

   /**
   * Unique identifier for this bundle.
   * @return bundleId
  **/
  @Schema(example = "af8fa035-3275-44fc-9a9b-a38c02efa114", description = "Unique identifier for this bundle.")
  public UUID getBundleId() {
    return bundleId;
  }

  public void setBundleId(UUID bundleId) {
    this.bundleId = bundleId;
  }

  public TransactionBundle reference(String reference) {
    this.reference = reference;
    return this;
  }

   /**
   * Reference for the bundle, can be matched with details on bank statement.
   * @return reference
  **/
  @Schema(example = "B20200101-ABCDE", description = "Reference for the bundle, can be matched with details on bank statement.")
  public String getReference() {
    return reference;
  }

  public void setReference(String reference) {
    this.reference = reference;
  }

  public TransactionBundle files(List<File> files) {
    this.files = files;
    return this;
  }

  public TransactionBundle addFilesItem(File filesItem) {
    if (this.files == null) {
      this.files = new ArrayList<File>();
    }
    this.files.add(filesItem);
    return this;
  }

   /**
   * The files representing these bundles.
   * @return files
  **/
  @Schema(description = "The files representing these bundles.")
  public List<File> getFiles() {
    return files;
  }

  public void setFiles(List<File> files) {
    this.files = files;
  }

  public TransactionBundle startDateTime(OffsetDateTime startDateTime) {
    this.startDateTime = startDateTime;
    return this;
  }

   /**
   * Timestamp indicating the start time of the bundle. Format: YYYY-MM-DD:HH:mm:ss.SSSZ.
   * @return startDateTime
  **/
  @Schema(example = "2019-09-09T00:00Z", description = "Timestamp indicating the start time of the bundle. Format: YYYY-MM-DD:HH:mm:ss.SSSZ.")
  public OffsetDateTime getStartDateTime() {
    return startDateTime;
  }

  public void setStartDateTime(OffsetDateTime startDateTime) {
    this.startDateTime = startDateTime;
  }

  public TransactionBundle endDateTime(OffsetDateTime endDateTime) {
    this.endDateTime = endDateTime;
    return this;
  }

   /**
   * Timestamp indicating the end time of the bundle. Format: YYYY-MM-DD:HH:mm:ss.SSSZ.
   * @return endDateTime
  **/
  @Schema(example = "2019-09-09T23:59:59.999Z", description = "Timestamp indicating the end time of the bundle. Format: YYYY-MM-DD:HH:mm:ss.SSSZ.")
  public OffsetDateTime getEndDateTime() {
    return endDateTime;
  }

  public void setEndDateTime(OffsetDateTime endDateTime) {
    this.endDateTime = endDateTime;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    TransactionBundle transactionBundle = (TransactionBundle) o;
    return Objects.equals(this.bundleId, transactionBundle.bundleId) &&
        Objects.equals(this.reference, transactionBundle.reference) &&
        Objects.equals(this.files, transactionBundle.files) &&
        Objects.equals(this.startDateTime, transactionBundle.startDateTime) &&
        Objects.equals(this.endDateTime, transactionBundle.endDateTime);
  }

  @Override
  public int hashCode() {
    return Objects.hash(bundleId, reference, files, startDateTime, endDateTime);
  }


  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class TransactionBundle {\n");
    
    sb.append("    bundleId: ").append(toIndentedString(bundleId)).append("\n");
    sb.append("    reference: ").append(toIndentedString(reference)).append("\n");
    sb.append("    files: ").append(toIndentedString(files)).append("\n");
    sb.append("    startDateTime: ").append(toIndentedString(startDateTime)).append("\n");
    sb.append("    endDateTime: ").append(toIndentedString(endDateTime)).append("\n");
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
