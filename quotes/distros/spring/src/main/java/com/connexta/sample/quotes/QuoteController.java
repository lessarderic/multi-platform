package com.connexta.sample.quotes;

import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import com.connexta.sample.quotes.api.Quote;
import com.connexta.sample.quotes.api.QuoteService;
import com.connexta.sample.quotes.openapi.model.AddQuoteModel;
import com.connexta.sample.quotes.openapi.model.QuoteModel;
import com.connexta.sample.quotes.openapi.stub.QuoteApi;
import com.google.common.base.Preconditions;

import io.swagger.annotations.Api;

/**
 * Spring Web controller class that exposes the {@code /quote} REST endpoint. This class' purpose is
 * to convert incoming REST requests into calls to the underlying {@link QuoteService} instance.
 *
 * <p>It also provides the REST endpoint documentation using Swagger 2 annotations.
 */
@RestController
public class QuoteController implements QuoteApi {

    private final QuoteService quoteService;

    /**
     * Constructs a new REST Spring Web controller.
     *
     * @param quoteService service where requests will be forwarded
     */
    public QuoteController(QuoteService quoteService) {
        Preconditions.checkNotNull(quoteService, "QuoteService cannot be null");
        this.quoteService = quoteService;
    }

    /**
     * Gets a random quote from the list of quotes that have been added.
     *
     * @return new quote model that will be serialized out to JSON
     */
    @Override
    public ResponseEntity<QuoteModel> getQuoteUsingGET() {
        return new ResponseEntity<>(createQuoteModel(quoteService.getQuote()), HttpStatus.OK);
    }

    /**
     * Adds a new quote to the list of quotes that can be retrieved.
     *
     * @param newQuote information about the new quote to add
     * @return new quote model that was created, including the new quote's unique ID
     */
    @Override
    public ResponseEntity<QuoteModel> addQuoteUsingPOST(@Valid AddQuoteModel newQuote) {
        return new ResponseEntity<>(createQuoteModel(quoteService.addQuote(newQuote.getAuthor(),
                newQuote.getContent())), HttpStatus.OK);
    }

    private static QuoteModel createQuoteModel(Quote quote) {
        QuoteModel quoteModel = new QuoteModel();
        quoteModel.setAuthor(quote.getAuthor());
        quoteModel.setContent(quote.getContent());
        quoteModel.setId(quote.getId());
        return quoteModel;
    }
}
