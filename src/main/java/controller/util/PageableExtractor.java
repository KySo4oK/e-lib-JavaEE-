package controller.util;

import model.entity.Pageable;

public class PageableExtractor {
    public static Pageable extractPageableFromUri(String requestURI) {
        String[] partsOfUri = requestURI.split("/");
        return Pageable.Builder.aPageable()
                .number(Integer.parseInt(partsOfUri[partsOfUri.length-1]))
                .page(Integer.parseInt(partsOfUri[partsOfUri.length-2]))
                .build();
    }
}
