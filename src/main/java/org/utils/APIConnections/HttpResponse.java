package org.utils.APIConnections;

import java.net.HttpURLConnection;
import java.util.Map;

public class HttpResponse
{
	private final Integer httpResponseCode;
	private final String responseMessage;
	private final String response;
	private final Map<String, String> headers;
	private final boolean isSuccess;

	private HttpResponse(Builder builder)
	{
		this.httpResponseCode = builder.httpResponseCode;
		this.responseMessage = builder.responseMessage;
		this.response = builder.response;
		this.headers = builder.headers;
		this.isSuccess = builder.isSuccess;
	}

	public Integer getHttpResponseCode()
	{
		return httpResponseCode;
	}

	public String getResponseMessage()
	{
		return responseMessage;
	}

	public String getResponse()
	{
		return response;
	}

	public Map<String, String> getHeaders()
	{
		return headers;
	}

	public boolean isSuccess()
	{
		return isSuccess;
	}

	public static class Builder
	{
		private Integer httpResponseCode;
		private String responseMessage;
		private String response;
		private Map<String, String> headers;
		private boolean isSuccess = false;

		public Builder setHttpResponseCode(int httpResponseCode)
		{
			this.httpResponseCode = httpResponseCode;
			if(httpResponseCode >= HttpURLConnection.HTTP_OK && httpResponseCode < HttpURLConnection.HTTP_BAD_REQUEST)
			{
				this.isSuccess = true;
			}
			return this;
		}

		public Builder setResponseMessage(String responseMessage)
		{
			this.responseMessage = responseMessage;
			return this;
		}

		public Builder setResponse(String response)
		{
			this.response = response;
			return this;
		}

		public Builder setHeaders(Map<String, String> headers)
		{
			this.headers = headers;
			return this;
		}

		public HttpResponse build()
		{
			return new HttpResponse(this);
		}
	}
}