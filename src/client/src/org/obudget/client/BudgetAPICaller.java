package org.obudget.client;

import com.allen_sauer.gwt.log.client.Log;
import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.http.client.UrlBuilder;
import com.google.gwt.json.client.JSONArray;
import com.google.gwt.jsonp.client.JsonpRequestBuilder;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;

class BudgetAPICaller extends JsonpRequestBuilder {
	private UrlBuilder url;

	public BudgetAPICaller() {
		url = new UrlBuilder();
		url.setHost(Window.Location.getHost());
		String port = Window.Location.getPort();
		try {
			url.setPort(Integer.parseInt( port ));
		} catch (Exception e) {}
		url.setPort(33333);
		url.setPath("00");
	}
	
	public void setCode( String code ) {
		url.setPath(code);
	}

	public void setParameter( String key, String value ) {
		url.setParameter(key, value);
	}
	
	public void go( final BudgetAPICallback callback ) {
		final String urlStr = url.buildString();
		requestObject(urlStr, new AsyncCallback<JavaScriptObject>() {
			@Override
			public void onSuccess(JavaScriptObject result) {
				Log.info("BudgerAPICaller::onSuccess url="+urlStr+" -result="+result);
				JSONArray array = new JSONArray(result);
				if ( array != null ) {
					callback.onSuccess(array);
				} else {
					Log.warn("BudgerAPICaller::onSuccess array==null");					
				}
			}

			@Override
			public void onFailure(Throwable caught) {
				//Window.alert("Failed to access API: "+caught.getMessage());
			}
			
		});		
	}
}
