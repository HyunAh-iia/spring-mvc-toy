package my.study.springmvc.controller;

public record ToySuccessResponse<T>(String result, T data) {

    public static <T> ToySuccessResponse<T> success(final T data) {
        return new ToySuccessResponse("success", data);
    }

    public static ToySuccessResponse<Void> empty() {
        return success(null);
    }
}
