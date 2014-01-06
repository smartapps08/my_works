package bd.org.basis.downloader;

import java.io.IOException;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.ResponseHandler;
import org.apache.http.util.EntityUtils;

public class ByteArrayResponseHandler implements ResponseHandler<byte[]> {

	@Override
	public byte[] handleResponse(HttpResponse response)
			throws ClientProtocolException, IOException {
		if(response.getStatusLine().getStatusCode()==HttpStatus.SC_OK)
		{
			HttpEntity entity=response.getEntity();
			return EntityUtils.toByteArray(entity); 
		}
		else
		{
			return null;
		}
		
	}

}
