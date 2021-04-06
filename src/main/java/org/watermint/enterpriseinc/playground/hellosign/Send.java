package org.watermint.enterpriseinc.playground.hellosign;

import com.hellosign.sdk.HelloSignClient;
import com.hellosign.sdk.HelloSignException;
import com.hellosign.sdk.resource.SignatureRequest;

public class Send {
    private static final String API_KEY = "";

    public static void main(String[] args) throws HelloSignException {
        HelloSignClient client = new HelloSignClient(API_KEY);

        SignatureRequest request = new SignatureRequest();
        request.setSubject("NDA");
        request.setMessage("Please sign this NDA before proceed the process.");
        request.addSigner("james@example.com", "James");
        request.addFileUrl("https://www.example.com/standard_nda.pdf");

        SignatureRequest response = client.sendSignatureRequest(request);
    }
}
