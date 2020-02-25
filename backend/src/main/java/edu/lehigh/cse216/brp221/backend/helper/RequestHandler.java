package edu.lehigh.cse216.brp221.backend.helper;

import edu.lehigh.cse216.brp221.backend.SimpleRequest;

import java.util.Map;

public interface RequestHandler<V extends SimpleRequest> {

    Object process(V value, Map<String, String> urlParams, boolean shouldReturnHtml);

}
