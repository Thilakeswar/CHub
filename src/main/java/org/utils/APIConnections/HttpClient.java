package org.utils.APIConnections;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.apache.commons.lang3.StringUtils;

public class HttpClient
{
	private static final Logger LOGGER = Logger.getLogger(HttpClient.class.getName());

	public static HttpResponse invoke(HttpRequestParams httpRequestParams) throws Exception
	{
		HttpResponse httpResponse = null;

		String urlStr = httpRequestParams.getUrl();
		String method = httpRequestParams.getMethod();
		int connectionTimeOut = httpRequestParams.getConnectionTimeOut();
		int readTimeOut = httpRequestParams.getReadTimeOut();
		Map<String, String> requestHeaderMap = httpRequestParams.getHeaders();
		String contentType = httpRequestParams.getContentType();
		String json = httpRequestParams.getJson();
		byte[] jsonBytes = new byte[0];
		if(StringUtils.isNotEmpty(json))
		{
			jsonBytes = json.getBytes("UTF-8");
		}

		URL url = new URL(urlStr);

		//url.openConnection()--> Initializes the connection instance
		HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();

		//.setRequestMethod()--> Sets the method with which the URL should be invoked.GET/POST/PUT/DELETE
		httpURLConnection.setRequestMethod(method);

		//.setConnectTimeout()--> Sets the Connection Timeout.
		httpURLConnection.setConnectTimeout(connectionTimeOut);

		//.setReadTimeout()--> Sets the Read Timeout.
		httpURLConnection.setReadTimeout(readTimeOut);

		//.setDoInput(true)--> Intends that this URLConnection is used for reading data.
		httpURLConnection.setDoInput(true);

		if(requestHeaderMap != null && !requestHeaderMap.isEmpty())
		{
			for(Map.Entry<String, String> mapElement : requestHeaderMap.entrySet())
			{
				//.setRequestProperty(headerName,value)-->Sets Request Header to the Http Connection
				httpURLConnection.setRequestProperty(mapElement.getKey(), mapElement.getValue());
			}
		}

		//Setting User-Agent as a Request Header
		httpURLConnection.setRequestProperty("User-Agent", HttpConstants.USER_AGENT);

		if(HttpConstants.METHOD_POST.equalsIgnoreCase(method)
			|| HttpConstants.METHOD_PUT.equalsIgnoreCase(method)
			|| HttpConstants.METHOD_DELETE.equalsIgnoreCase(method))
		{
			//.setDoOutput(true)--> Intends that this URLConnection is used for POST/PUT/DELETE request
			httpURLConnection.setDoOutput(true);

			httpURLConnection.setRequestProperty("Content-Length", "" + jsonBytes.length);
			if(StringUtils.isNotEmpty(contentType))
			{
				httpURLConnection.setRequestProperty("Content-Type",
					"" + contentType + ";charset=" + "UTF-8");
			}
			else
			{
				httpURLConnection.setRequestProperty("Content-Type",
					"application/x-www-form-urlencoded;charset=UTF-8");
			}
		}

		try
		{

			httpURLConnection.connect();

			if(HttpConstants.METHOD_POST.equalsIgnoreCase(method)
				|| HttpConstants.METHOD_PUT.equalsIgnoreCase(method))
			{
				OutputStream out = httpURLConnection.getOutputStream();
				try
				{
					out.write(jsonBytes);
				}
				catch(Exception e)
				{
					LOGGER.log(Level.INFO, "HttpClient Logger : Exception occurred"
						+ " during writing data to connection due to ", e);
				}
				finally
				{
					if(out != null)
					{
						out.close();
					}
				}
			}
			httpResponse = constructResponse(httpURLConnection);
		}
		catch(Exception e)
		{
			LOGGER.log(Level.INFO, "HttpClient Logger : Exception occurred "
				+ "during connecting to the URL mentioned due to ", e);
		}
		finally
		{
			httpURLConnection.disconnect();
		}
		if(httpRequestParams.canLogResponse())
		{
			LOGGER.log(Level.INFO, "HttpClient Logger : URL Call successful"
				+ "\nURL : {0}"
				+ "\nHTTP Method : {1}"
				+ "\nHTTP Response Code : {2}"
				+ "\nHTTP Response Message : {3}"
				+ "\nResponse : {4}",
				new Object[] {
					httpRequestParams.getUrl(),
					httpRequestParams.getMethod(),
					httpResponse.getHttpResponseCode(),
					httpResponse.getResponseMessage(),
					httpResponse.getResponse()
			});
		}
		return httpResponse;
	}

	public static HttpResponse constructResponse(HttpURLConnection httpURLConnection) throws Exception
	{
		int responseCode = httpURLConnection.getResponseCode();
		String responseMsg = httpURLConnection.getResponseMessage();
		String response = "";

		InputStream is = null;
		try
		{
			is = responseCode >= HttpURLConnection.HTTP_OK
				&& responseCode < HttpURLConnection.HTTP_MULT_CHOICE
				? httpURLConnection.getInputStream() : httpURLConnection.getErrorStream();
			if(is == null)
			{
				LOGGER.log(Level.INFO, "HttpClient Logger : Input stream returned is NULL");
			}
			else
			{
				InputStreamReader reader = null;
				try
				{
					StringBuilder buffer = new StringBuilder();
					reader = new InputStreamReader(is, "UTF-8");
					int bytesRead;
					char[] charBuf = new char[1024];
					while((bytesRead = reader.read(charBuf, 0, charBuf.length)) > 0)
					{
						buffer.append(charBuf, 0, bytesRead);
					}
					response = buffer.toString();
				}
				catch(Exception e)
				{
					LOGGER.log(Level.INFO, "HttpClient Logger : Exception occurred "
						+ "during reading the response data due to ", e);
				}
				finally
				{
					if(reader != null)
					{
						reader.close();
					}
				}
			}
		}
		catch(Exception e)
		{
			LOGGER.log(Level.INFO, "HttpClient Logger : Exception occurred "
				+ "during reading the response data due to ", e);
		}
		finally
		{
			if(is != null)
			{
				is.close();
			}
		}

		Map<String, String> responseHeadersMap = new HashMap();

		Map<String, List<String>> responseHeaderFields = httpURLConnection.getHeaderFields();
		for(Map.Entry responseHeader : responseHeaderFields.entrySet())
		{
			String headerName = (String) responseHeader.getKey();
			StringBuilder headerValue = new StringBuilder();
			List<String> headerValues = (List) responseHeader.getValue();
			for(String headerVal : headerValues)
			{
				headerValue.append(headerVal);
			}
			responseHeadersMap.put(headerName, headerValue.toString());
		}

		HttpResponse httpResponse = new HttpResponse.Builder()
			.setHttpResponseCode(responseCode)
			.setResponseMessage(responseMsg)
			.setResponse(response)
			.setHeaders(responseHeadersMap)
			.build();
		return httpResponse;
	}
}