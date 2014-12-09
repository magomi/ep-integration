package de.intecsoft.dev;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.config.ClientConfig;
import com.sun.jersey.api.client.config.DefaultClientConfig;
import com.sun.jersey.api.client.filter.HTTPBasicAuthFilter;
import com.sun.jersey.core.util.MultivaluedMapImpl;
import de.intecsoft.dev.ep.AuthorCreationResponseData;
import de.intecsoft.dev.ep.GroupCreationResponseData;
import de.intecsoft.dev.ep.PadCreationResponseData;
import de.intecsoft.dev.ep.SessionCreationResponseData;
import org.apache.wicket.markup.ComponentTag;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.request.cycle.RequestCycle;
import org.apache.wicket.request.http.WebResponse;

import javax.servlet.http.Cookie;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;

public class HomePage extends WebPage {
    private static final long serialVersionUID = 1L;

    private AuthorCreationResponseData authorCreationResponseData;
    private GroupCreationResponseData groupCreationResponseData;
    private PadCreationResponseData padCreationResponseData;
    private SessionCreationResponseData sessionCreationResponseData;


    private String etherpadBaseUrl = "http://localhost:9001/";
    private String etherpadApiPath = "api/1/";
    private String etherpadPadPath = "p/";
    private String userId = "mgt";
    private String groupId = "somegroup";
    private String userName = "marco.grunert@intecsoft.de";
    private String apiKey = "1b8d33823ec9a3e2163633aecf7ba09132589a28fd71272b2a74295acd11f64a";
    private String padId = "myPanelId";
//    private String apiAuthUser = "api";
//    private String apiAuthPass = "api";

    public HomePage() {
        super();
        setDefaultModel(new CompoundPropertyModel<HomePage>(this));

        initPadAuth();

        add(new WebMarkupContainer("protocolFrame") {
            @Override
            protected void onComponentTag(final ComponentTag tag) {
                super.onComponentTag(tag);
                tag.put("src", etherpadBaseUrl + etherpadPadPath + groupCreationResponseData.getData().getGroupID() + "$" + padId);
                tag.put("height", "480px");
                tag.put("width", "100%");
            }
        });

        add(new TextField("userId"));
        add(new TextField("userName"));
        add(new TextField("groupId"));
        add(new TextField("sessionCreationResponseData.data.sessionID"));
        add(new TextField("groupCreationResponseData.data.groupID"));
        add(new TextField("authorCreationResponseData.data.authorID"));
    }

    private void initPadAuth() {
        final ClientConfig config = new DefaultClientConfig();
        final Client client = Client.create(config);
//        client.addFilter(new HTTPBasicAuthFilter(apiAuthUser, apiAuthPass));


        // siehe http://etherpad.org/doc/v1.4.1/#index_http_api

        // 1) Author Creation
        //    Request: http://pad.domain/api/1/createAuthorIfNotExistsFor?apikey=secret&name=Michael&authorMapper=7
        //    Response: {code: 0, message:"ok", data: {authorID: "a.s8oes9dhwrvt0zif"}}
        WebResource service = client.resource(etherpadBaseUrl + etherpadApiPath + "createAuthorIfNotExistsFor");
        MultivaluedMap<String, String> params = new MultivaluedMapImpl();
        params.add("apikey", apiKey);
        params.add("name", userName);
        params.add("authorMapper", userId);
        ClientResponse response =
                service.queryParams(params).accept(MediaType.APPLICATION_JSON_TYPE).get(ClientResponse.class);
        authorCreationResponseData =
                response.getEntity(AuthorCreationResponseData.class);

        // 2) Group Creation
        //    Request: http://pad.domain/api/1/createGroupIfNotExistsFor?apikey=secret&groupMapper=7
        //    Response: {code: 0, message:"ok", data: {groupID: "g.s8oes9dhwrvt0zif"}}
        service = client.resource(etherpadBaseUrl + etherpadApiPath + "createGroupIfNotExistsFor");
        params = new MultivaluedMapImpl();
        params.add("apikey", apiKey);
        params.add("groupMapper", groupId);
        response
                = service.queryParams(params).accept(MediaType.APPLICATION_JSON_TYPE).get(ClientResponse.class);
        groupCreationResponseData =
                response.getEntity(GroupCreationResponseData.class);

        // 3) Pad Creation
        //    Request: http://pad.domain/api/1/createGroupPad?apikey=secret&groupID=g.s8oes9dhwrvt0zif&padName=samplePad&text=This is the first sentence in the pad
        //    Response: {code: 0, message:"ok", data: null}
        service = client.resource(etherpadBaseUrl + etherpadApiPath + "createGroupPad");
        params = new MultivaluedMapImpl();
        params.add("apikey", apiKey);
        params.add("groupID", groupCreationResponseData.getData().getGroupID());
        params.add("padName", groupCreationResponseData.getData().getGroupID() + "$" + padId);
        params.add("text", "Pad for " + userName + " ([" + authorCreationResponseData.getData().getAuthorID()
                + "]/[" + groupCreationResponseData.getData().getGroupID() + "])");
        response = service.queryParams(params).accept(MediaType.APPLICATION_JSON_TYPE).get(ClientResponse.class);
        padCreationResponseData = response.getEntity(PadCreationResponseData.class);

        // 4) Session Creation
        //    Request: http://pad.domain/api/1/createSession?apikey=secret&groupID=g.s8oes9dhwrvt0zif&authorID=a.s8oes9dhwrvt0zif&validUntil=1312201246
        //    Response: {"data":{"sessionID": "s.s8oes9dhwrvt0zif"}}
        service = client.resource(etherpadBaseUrl + etherpadApiPath + "createSession");
        params = new MultivaluedMapImpl();
        params.add("apikey", apiKey);
        params.add("groupID", groupCreationResponseData.getData().getGroupID());
        params.add("authorID", authorCreationResponseData.getData().getAuthorID());
        params.add("validUntil", (new Long((System.currentTimeMillis()/1000) + 10)).toString());
        response = service.queryParams(params).accept(MediaType.APPLICATION_JSON_TYPE).get(ClientResponse.class);
        sessionCreationResponseData
                = response.getEntity(SessionCreationResponseData.class);

        // 5) Portal places the cookie "sessionID" with the given value on the client and creates an iframe including the pad.
        WebResponse webResponse = (WebResponse) RequestCycle.get().getResponse();
        Cookie cookie = new Cookie("sessionID", sessionCreationResponseData.getData().getSessionID());
        webResponse.addCookie(cookie);
    }
}
