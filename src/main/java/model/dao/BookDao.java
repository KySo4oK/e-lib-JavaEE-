package model.dao;

import model.entity.Book;

public interface BookDao extends GenericDao<Book> {
    String SQL_FIND_ALL = "select * \n" +
            "from book\n" +
            "left join book_tag on book.book_id = book_tag.book_id left join tag on book_tag.tag_id = tag.tag_id\n" +
            "    left join book_author on book.book_id = book_author.book_id left join author on book_author.author_id = author.author_id";
    String SQL_FIND_BY_ID = "select * from book where book_id = ? \n" +
            "left join book_tag on book.book_id = book_tag.book_id left join tag on book_tag.tag_id = tag.tag_id\n" +
            "    left join book_author on book.book_id = book_author.book_id left join author on book_author.author_id = author.author_id;";
    ;
    String SQL_FIND_BY_NAME = "select * from book where name = ? \n" +
            "left join book_tag on book.book_id = book_tag.book_id left join tag on book_tag.tag_id = tag.tag_id\n" +
            "    left join book_author on book.book_id = book_author.book_id left join author on book_author.author_id = author.author_id;";
    String SQL_FIND_BY_NAME_UA = "select * from book where name_ua = ? \n" +
            "left join book_tag on book.book_id = book_tag.book_id left join tag on book_tag.tag_id = tag.tag_id\n" +
            "    left join book_author on book.book_id = book_author.book_id left join author on book_author.author_id = author.author_id;";
    String SQL_INSERT = "insert into book (name, name_ua, available) values (?, ?, ?)";
    String SQL_UPDATE = "update book set available where book_id = ?";
    String SQL_DELETE = "delete from book where book_id = ?";

    Book findByName(String name);

    Book findByNameUa(String nameUa);
}
