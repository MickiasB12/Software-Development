package edu.lehigh.cse216.brp221.backend.handler;

import edu.lehigh.cse216.brp221.backend.Database;
import edu.lehigh.cse216.brp221.backend.SimpleRequest;
import edu.lehigh.cse216.brp221.backend.StructuredResponse;
import edu.lehigh.cse216.brp221.backend.helper.AbstractRequestHandler;
import edu.lehigh.cse216.brp221.backend.helper.Answer;
import edu.lehigh.cse216.brp221.backend.helper.Model;

import java.util.ArrayList;
import java.util.Map;

public class GetSelectOneHandlers extends AbstractRequestHandler<SimpleRequest> {

    private Model model;

    public GetSelectOneHandlers(Model model) {
        super(SimpleRequest.class, model);
        this.model = model;
    }

    @Override
    protected Answer processImpl(SimpleRequest value, Map<String, String> urlParams, boolean shouldReturnHtml) {
        int id = Integer.parseInt(urlParams.get(":id"));
        Database.RowData data = model.selectOne(id);
        if (data == null) {
            return new Answer(200,
                    gson.toJson(new StructuredResponse("error", id + " not found", null)));
        } else {
            return new Answer(200, gson.toJson(new StructuredResponse("ok", null, data)));
        }
    }
}
