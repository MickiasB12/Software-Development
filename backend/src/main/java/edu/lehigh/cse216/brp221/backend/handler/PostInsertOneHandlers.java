package edu.lehigh.cse216.brp221.backend.handler;

import edu.lehigh.cse216.brp221.backend.Database;
import edu.lehigh.cse216.brp221.backend.SimpleRequest;
import edu.lehigh.cse216.brp221.backend.StructuredResponse;
import edu.lehigh.cse216.brp221.backend.helper.AbstractRequestHandler;
import edu.lehigh.cse216.brp221.backend.helper.Answer;
import edu.lehigh.cse216.brp221.backend.helper.Model;

import java.util.Map;

public class PostInsertOneHandlers extends AbstractRequestHandler<SimpleRequest> {

    private Model model;

    public PostInsertOneHandlers(Model model) {
        super(SimpleRequest.class, model);
        this.model = model;
    }

    @Override
    protected Answer processImpl(SimpleRequest value, Map<String, String> urlParams, boolean shouldReturnHtml) {

        int newId = model.insertRow(value.mTitle, value.mMessage, 0);
        if (newId == -1) {
            return new Answer(200,
                    gson.toJson(new StructuredResponse("error", "error performing insertion", null)));
        } else {
            return new Answer(200, gson.toJson(new StructuredResponse("ok", "" + newId, null)));
        }
    }
}
