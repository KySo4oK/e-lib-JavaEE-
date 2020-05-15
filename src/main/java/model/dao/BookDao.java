package model.dao;

import model.entity.Book;

import java.util.Optional;

public interface BookDao extends GenericDao<Book> {
    String SQL_FIND_ALL = "select book.book_id     as \"bookId\",\n" +
            "       book.name        as \"bookName\",\n" +
            "       book.name_ua     as \"bookNameUa\",\n" +
            "       book.available,\n" +
            "       tag.tag_id       as \"tagId\",\n" +
            "       tag.name_ua      as \"tagNameUa\",\n" +
            "       tag.name         as \"tagName\",\n" +
            "       author.author_id as \"authorId\",\n" +
            "       author.name_ua   as \"authorNameUa\",\n" +
            "       author.name      as \"authorName\",\n" +
            "       shelf.shelf_id   as \"shelf_id\"\n" +
            "from book\n" +
            "         left join book_tag on book.book_id = book_tag.book_id\n" +
            "         left join tag on book_tag.tag_id = tag.tag_id\n" +
            "         left join book_author on book.book_id = book_author.book_id\n" +
            "         left join author on book_author.author_id = author.author_id\n" +
            "         left join shelf on book.book_id = shelf.book_id";
    String SQL_FIND_BY_ID = SQL_FIND_ALL + " where book.book_id = ?";
    String SQL_FIND_BY_NAME = SQL_FIND_ALL + " where book.name = ?";
    String SQL_FIND_BY_NAME_UA = SQL_FIND_ALL + " where book.name_ua = ?";
    String SQL_INSERT = "insert into book (name, name_ua, available) values (?, ?, ?)";
    String SQL_UPDATE = "update book set available = ? where book_id = ?";
    String SQL_DELETE = "delete from book where book_id = ?";

    Optional<Book> findByName(String name);

    Optional<Book> findByNameUa(String nameUa);
}
