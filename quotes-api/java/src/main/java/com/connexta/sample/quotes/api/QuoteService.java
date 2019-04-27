/**
 * Copyright (c) Connexta
 *
 * <p>This is free software: you can redistribute it and/or modify it under the terms of the GNU
 * Lesser General Public License as published by the Free Software Foundation, either version 3 of
 * the License, or any later version.
 *
 * <p>This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY;
 * without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU Lesser General Public License for more details. A copy of the GNU Lesser General Public
 * License is distributed along with this program and can be found at
 * <http://www.gnu.org/licenses/lgpl.html>.
 */
package com.connexta.sample.quotes.api;

/**
 * Service that produces {@link Quote}s.
 */
public interface QuoteService {

    /**
     * @return gets a random {@link Quote}
     */
    Quote getQuote();

    /**
     * Adds a new {@link Quote}.
     *
     * @param author  quote's author. Cannot be empty, or have more than {@value
     *                Quote#MAX_AUTHOR_LENGTH} characters.
     * @param content quote's content. Cannot be empty, or have more that {@value
     *                Quote#MAX_CONTENT_LENGTH} characters.
     * @return new {@link Quote} added to the list
     * @throws IllegalArgumentException when {@code author} or {@code content} are invalid
     */
    Quote addQuote(String author, String content);
}
