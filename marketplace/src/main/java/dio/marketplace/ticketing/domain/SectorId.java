package dio.marketplace.ticketing.domain;

import org.springframework.util.Assert;

/**
 * A classe SectorId representa o identificador de um setor no sistema de ticketing.
 * Ela é usada para criar um identificador único para cada setor.
 *
 * A classe é implementada como um record, o que significa que ela é imutável e possui um construtor
 * que recebe uma string representando o identificador do setor.
 *
 * O construtor da classe utiliza a classe Assert do Spring Framework para garantir que o valor do
 * identificador não seja nulo. Se o valor for nulo, uma exceção IllegalArgumentException será lançada
 * com a mensagem "id must not be null".
 */
public record SectorId(String id) {
    public SectorId{
        Assert.notNull(id, "id must not be null");
    }
}
