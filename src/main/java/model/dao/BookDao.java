package model.dao;

import model.entity.Book;

public interface BookDao extends GenericDao<Book> {
    String SQL_FIND_ALL = "select book.book_id as \"bookId\", book.name as \"bookName\"," +
            " book.name_ua as \"bookNameUa\", book.available,\n" +
            " tag.tag_id as \"tagId\", tag.name_ua as \"tagNameUa\", tag.name as \"tagName\",\n" +
            " author.author_id as \"authorId\", author.name_ua as \"authorNameUa\", author.name as \"authorName\"\n" +
            "from book\n" +
            "    left join book_tag on book.book_id = book_tag.book_id left join tag on book_tag.tag_id = tag.tag_id\n" +
            "    left join book_author on book.book_id = book_author.book_id " +
            "left join author on book_author.author_id = author.author_id";
    String SQL_FIND_BY_ID = SQL_FIND_ALL + " where book.book_id = ?";
    String SQL_FIND_BY_NAME = SQL_FIND_ALL + " where book.name = ?";
    String SQL_FIND_BY_NAME_UA = SQL_FIND_ALL + " where book.name_ua = ?";
    String SQL_INSERT = "insert into book (name, name_ua, available) values (?, ?, ?)";
    String SQL_UPDATE = "update book set available where book_id = ?";
    String SQL_DELETE = "delete from book where book_id = ?";

    Book findByName(String name);

    Book findByNameUa(String nameUa);
}
