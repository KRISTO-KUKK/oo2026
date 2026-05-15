package ee.mihkel.veebipood.dto;

public record OrderRowDto(
        Long productId,
        int quantity
) {
}
