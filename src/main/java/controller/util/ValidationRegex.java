package controller.util;

public interface ValidationRegex {
    String bookName = "^[a-zA-Z0-9._]{2,30}$";
    String bookNameUa = "^[а-щА-ЩЬьЮюЯяЇїІіЄєҐґ0-9._]{2,30}$";
    String usernameRegex = "^(?=.{5,20}$)(?![_.])(?!.*[_.]{2})[а-щА-ЩЬьЮюЯяЇїІіЄєҐґa-zA-Z0-9._]+(?<![_.])$";
    String passwordRegex = ".{4,30}";
    String phoneRegex = "^[+]*[(]?[0-9]{1,4}[)]?[-\\s./0-9]*$";
    String emailRegex = "^\\w+@[a-zA-Z_]+?\\.[a-zA-Z]{2,3}$";
}