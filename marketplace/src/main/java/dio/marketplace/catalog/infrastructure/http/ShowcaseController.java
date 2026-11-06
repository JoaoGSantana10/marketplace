package dio.marketplace.catalog.infrastructure.http;

import dio.marketplace.catalog.application.BrowseShowcaseUseCase;
import dio.marketplace.catalog.application.dto.EventOutput;
import dio.marketplace.catalog.domain.Event;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Class ShowcaseController do módulo infrastructure.
 * Representa o contrato, modelo ou componente indicado pelo próprio nome da declaração.
 * Mantém as regras e responsabilidades descritas pela implementação, sem expor detalhes de infraestrutura desnecessários aos consumidores.
 */
@RestController
@RequestMapping("/showcase")
public class ShowcaseController {
/**
 * Armazena o atributo browseShowcaseUseCase e o estado necessário ao componente.
 */
    private final BrowseShowcaseUseCase browseShowcaseUseCase;

/**
 * Executa a operação ShowcaseController, conforme o papel desta classe no fluxo da aplicação.
 * A operação preserva as invariantes do domínio e delega persistência ou integração aos componentes apropriados.
 * @param browseShowcaseUseCase parâmetro utilizado pela operação.
 */
    public ShowcaseController(BrowseShowcaseUseCase browseShowcaseUseCase) {
        this.browseShowcaseUseCase = browseShowcaseUseCase;
    }

    @GetMapping
    List<EventOutput> BrowseShowCase(){
        return browseShowcaseUseCase.execute();
    }
}
