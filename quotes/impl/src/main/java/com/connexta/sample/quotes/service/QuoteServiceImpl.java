package com.connexta.sample.quotes.service;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.connexta.sample.quotes.api.Quote;
import com.connexta.sample.quotes.api.QuoteService;

/**
 * Implementation of the {@link QuoteService} interface that creates immutable {@link Quote}
 * objects.
 */
public class QuoteServiceImpl implements QuoteService {
  private static final Logger LOGGER = LoggerFactory.getLogger(QuoteServiceImpl.class);

  // TODO - Replace with DB
  private static final Map<Integer, Quote> QUOTES = new HashMap<>();

  // Quotes from https://quotes-generator.com/quotes-generator.php
  static {
    QUOTES.put(0, newQuote(0, "Why be average when you can be extraordinary?", "Avina Celeste"));
    QUOTES.put(1, newQuote(1, "The price of greatness is responsibility.", "Winston Churchill"));
    QUOTES.put(
        2,
        newQuote(
            2,
            "You are your biggest obstacle, but you are also the change that you seek.",
            "Freequill"));
  }

  @Override
  public Quote getQuote() {
    Quote quote = QUOTES.get(new Random().nextInt(100) % QUOTES.size());
    LOGGER.info("Quote selected: {}", quote);
    return quote;
  }

  @Override
  public Quote addQuote(final String author, final String content) {
    Quote quote = newQuote(QUOTES.size(), content, author);
    QUOTES.put(QUOTES.size(), quote);
    return quote;
  }

  private static Quote newQuote(int id, String content, String author) {
    return ImmutableQuoteImpl.builder().id(id).content(content).author(author).build();
  }
}
