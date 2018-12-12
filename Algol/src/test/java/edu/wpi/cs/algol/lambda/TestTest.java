package edu.wpi.cs.algol.lambda;

import static org.junit.Assert.*;

public class TestTest {

/* public class ValidateCreateConstant {

	Context createContext(String apiCall) {
        TestContext ctx = new TestContext();
        ctx.setFunctionName(apiCall);
        return ctx;
    }

    @Test
    public void testCreateAndChangeConstant() throws IOException {
        CreateConstantHandler handler = new CreateConstantHandler();

        int rnd = (int) (Math.random() * 1000000);
        CreateConstantRequest ar = new CreateConstantRequest("x" + rnd, 13.2872);
        
        String ccRequest = new Gson().toJson(ar);
        String jsonRequest = new Gson().toJson(new PostRequest(ccRequest));
        
        InputStream input = new ByteArrayInputStream(jsonRequest.getBytes());
        OutputStream output = new ByteArrayOutputStream();

        handler.handleRequest(input, output, createContext("create"));

        PostResponse post = new Gson().fromJson(output.toString(), PostResponse.class);
        CreateConstantResponse resp = new Gson().fromJson(post.body, CreateConstantResponse.class);
        System.out.println(resp);
        
        Assert.assertEquals("Successfully defined constant:x" + rnd, resp.response);
        
        // now change
        
        ar = new CreateConstantRequest("x" + rnd, 99.12345);
        
        ccRequest = new Gson().toJson(ar);
        jsonRequest = new Gson().toJson(new PostRequest(ccRequest));
        
        input = new ByteArrayInputStream(jsonRequest.getBytes());
        output = new ByteArrayOutputStream();

        handler.handleRequest(input, output, createContext("create"));

        post = new Gson().fromJson(output.toString(), PostResponse.class);
        resp = new Gson().fromJson(post.body, CreateConstantResponse.class);
        System.out.println(resp);
        
        Assert.assertEquals("Successfully defined constant:x" + rnd, resp.response);
    }

}
*/

}
