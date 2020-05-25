package model.entity;

public class Pageable {
    private int page;
    private int number;

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public static final class Builder {
        private int page;
        private int number;

        private Builder() {
        }

        public static Builder aPageable() {
            return new Builder();
        }

        public Builder page(int page) {
            this.page = page;
            return this;
        }

        public Builder number(int number) {
            this.number = number;
            return this;
        }

        public Pageable build() {
            Pageable pageable = new Pageable();
            pageable.setPage(page);
            pageable.setNumber(number);
            return pageable;
        }
    }
}
