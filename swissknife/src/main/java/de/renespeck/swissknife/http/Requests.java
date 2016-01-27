package de.renespeck.swissknife.http;

import java.io.IOException;

import org.apache.commons.io.IOUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.fluent.Form;
import org.apache.http.client.fluent.Request;
import org.apache.http.client.fluent.Response;
import org.apache.http.entity.ContentType;
import org.apache.http.util.EntityUtils;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.json.JSONObject;

import de.renespeck.swissknife.cfg.Const;

public class Requests {
  public static final Logger LOG = LogManager.getLogger(Requests.class);

  public static String postForm(final String url, final Form form)
      throws ClientProtocolException, IOException {

    final Response response = Request.Post(url)
        .addHeader("Content-Type", ContentType.APPLICATION_FORM_URLENCODED.getMimeType())
        .addHeader("Accept-Charset", Const.UTF_8.name()).bodyForm(form.build()).execute();

    final HttpResponse httpResponse = response.returnResponse();
    LOG.info(httpResponse.getStatusLine());
    final HttpEntity entry = httpResponse.getEntity();
    final String r = IOUtils.toString(entry.getContent(), Const.UTF_8);
    EntityUtils.consume(entry);
    return r;
  }

  public static String postJson(final String url, final JSONObject json)
      throws ClientProtocolException, IOException {
    final Response response =
        Request.Post(url).addHeader("Content-Type", ContentType.APPLICATION_JSON.getMimeType())
            .addHeader("Accept-Charset", Const.UTF_8.name())
            .bodyString(json.toString(), ContentType.APPLICATION_JSON).execute();

    final HttpResponse httpResponse = response.returnResponse();
    LOG.info(httpResponse.getStatusLine());
    final HttpEntity entry = httpResponse.getEntity();
    final String r = IOUtils.toString(entry.getContent(), Const.UTF_8);
    EntityUtils.consume(entry);
    return r;
  }
}
