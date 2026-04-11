package infnet.tp3_springboot.service;

import co.elastic.clients.elasticsearch._types.aggregations.Aggregate;
import co.elastic.clients.elasticsearch._types.aggregations.Aggregation;
import co.elastic.clients.elasticsearch._types.aggregations.AggregationRange;
import co.elastic.clients.elasticsearch._types.aggregations.RangeBucket;
import co.elastic.clients.elasticsearch._types.aggregations.StringTermsBucket;
import infnet.tp3_springboot.domain.loja.ProdutoLoja;
import org.springframework.data.elasticsearch.client.elc.ElasticsearchAggregations;
import org.springframework.data.elasticsearch.client.elc.NativeQuery;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.SearchHit;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.data.elasticsearch.core.query.Criteria;
import org.springframework.data.elasticsearch.core.query.CriteriaQuery;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class ProdutoLojaService {

    private final ElasticsearchOperations elasticsearchOperations;

    public ProdutoLojaService(ElasticsearchOperations elasticsearchOperations) {
        this.elasticsearchOperations = elasticsearchOperations;
    }

    public List<ProdutoLoja> buscarPorNome(String termo) {
        Criteria criteria = new Criteria("nome").matches(termo);
        CriteriaQuery query = new CriteriaQuery(criteria);
        return elasticsearchOperations.search(query, ProdutoLoja.class)
                .getSearchHits().stream().map(SearchHit::getContent).collect(Collectors.toList());
    }

    public List<ProdutoLoja> buscarPorDescricao(String termo) {
        Criteria criteria = new Criteria("descricao").matches(termo);
        CriteriaQuery query = new CriteriaQuery(criteria);
        return elasticsearchOperations.search(query, ProdutoLoja.class)
                .getSearchHits().stream().map(SearchHit::getContent).collect(Collectors.toList());
    }

    public List<ProdutoLoja> buscarPorFraseExata(String termo) {
        Criteria criteria = new Criteria("descricao").contains(termo);
        CriteriaQuery query = new CriteriaQuery(criteria);
        return elasticsearchOperations.search(query, ProdutoLoja.class)
                .getSearchHits().stream().map(SearchHit::getContent).collect(Collectors.toList());
    }

    public List<ProdutoLoja> buscarFuzzy(String termo) {
        Criteria criteria = new Criteria("nome").fuzzy(termo);
        CriteriaQuery query = new CriteriaQuery(criteria);
        return elasticsearchOperations.search(query, ProdutoLoja.class)
                .getSearchHits().stream().map(SearchHit::getContent).collect(Collectors.toList());
    }

    public List<ProdutoLoja> buscarMultiCampos(String termo) {
        Criteria criteria = new Criteria("nome").matches(termo).or(new Criteria("descricao").matches(termo));
        CriteriaQuery query = new CriteriaQuery(criteria);
        return elasticsearchOperations.search(query, ProdutoLoja.class)
                .getSearchHits().stream().map(SearchHit::getContent).collect(Collectors.toList());
    }

    public List<ProdutoLoja> buscarTextoComFiltro(String termo, String categoria) {
        Criteria criteria = new Criteria("descricao").matches(termo).and(new Criteria("categoria").is(categoria));
        CriteriaQuery query = new CriteriaQuery(criteria);
        return elasticsearchOperations.search(query, ProdutoLoja.class)
                .getSearchHits().stream().map(SearchHit::getContent).collect(Collectors.toList());
    }

    public List<ProdutoLoja> buscarPorFaixaPreco(Double min, Double max) {
        Criteria criteria = new Criteria("preco").greaterThanEqual(min).lessThanEqual(max);
        CriteriaQuery query = new CriteriaQuery(criteria);
        return elasticsearchOperations.search(query, ProdutoLoja.class)
                .getSearchHits().stream().map(SearchHit::getContent).collect(Collectors.toList());
    }

    public List<ProdutoLoja> buscarAvancada(String categoria, String raridade, Double min, Double max) {
        Criteria criteria = new Criteria("categoria").is(categoria)
                .and(new Criteria("raridade").is(raridade))
                .and(new Criteria("preco").greaterThanEqual(min).lessThanEqual(max));
        CriteriaQuery query = new CriteriaQuery(criteria);
        return elasticsearchOperations.search(query, ProdutoLoja.class)
                .getSearchHits().stream().map(SearchHit::getContent).collect(Collectors.toList());
    }

    public Map<String, Long> agruparPorCategoria() {
        NativeQuery query = NativeQuery.builder()
                .withAggregation("categorias", Aggregation.of(a -> a.terms(t -> t.field("categoria"))))
                .build();

        SearchHits<ProdutoLoja> hits = elasticsearchOperations.search(query, ProdutoLoja.class);
        Map<String, Long> resultado = new HashMap<>();

        if (hits.hasAggregations()) {
            ElasticsearchAggregations aggregations = (ElasticsearchAggregations) hits.getAggregations();
            Aggregate aggregate = aggregations.aggregationsAsMap().get("categorias").aggregation().getAggregate();

            for (StringTermsBucket bucket : aggregate.sterms().buckets().array()) {
                resultado.put(bucket.key().stringValue(), bucket.docCount());
            }
        }
        return resultado;
    }

    public Map<String, Long> agruparPorRaridade() {
        NativeQuery query = NativeQuery.builder()
                .withAggregation("raridades", Aggregation.of(a -> a.terms(t -> t.field("raridade"))))
                .build();

        SearchHits<ProdutoLoja> hits = elasticsearchOperations.search(query, ProdutoLoja.class);
        Map<String, Long> resultado = new HashMap<>();

        if (hits.hasAggregations()) {
            ElasticsearchAggregations aggregations = (ElasticsearchAggregations) hits.getAggregations();
            Aggregate aggregate = aggregations.aggregationsAsMap().get("raridades").aggregation().getAggregate();

            for (StringTermsBucket bucket : aggregate.sterms().buckets().array()) {
                resultado.put(bucket.key().stringValue(), bucket.docCount());
            }
        }
        return resultado;
    }

    public Double obterPrecoMedio() {
        NativeQuery query = NativeQuery.builder()
                .withAggregation("preco_medio", Aggregation.of(a -> a.avg(avg -> avg.field("preco"))))
                .build();

        SearchHits<ProdutoLoja> hits = elasticsearchOperations.search(query, ProdutoLoja.class);

        if (hits.hasAggregations()) {
            ElasticsearchAggregations aggregations = (ElasticsearchAggregations) hits.getAggregations();
            Aggregate aggregate = aggregations.aggregationsAsMap().get("preco_medio").aggregation().getAggregate();
            return aggregate.avg().value();
        }
        return 0.0;
    }

    public Map<String, Long> agruparPorFaixaPreco() {
        NativeQuery query = NativeQuery.builder()
                .withAggregation("faixas", Aggregation.of(a -> a.range(r -> r.field("preco")
                        .ranges(AggregationRange.of(ar -> ar.to(100.0)))
                        .ranges(AggregationRange.of(ar -> ar.from(100.0).to(300.0)))
                        .ranges(AggregationRange.of(ar -> ar.from(300.0).to(700.0)))
                        .ranges(AggregationRange.of(ar -> ar.from(700.0)))
                )))
                .build();

        SearchHits<ProdutoLoja> hits = elasticsearchOperations.search(query, ProdutoLoja.class);
        Map<String, Long> resultado = new HashMap<>();

        if (hits.hasAggregations()) {
            ElasticsearchAggregations aggregations = (ElasticsearchAggregations) hits.getAggregations();
            Aggregate aggregate = aggregations.aggregationsAsMap().get("faixas").aggregation().getAggregate();

            for (RangeBucket bucket : aggregate.range().buckets().array()) {
                String chave = bucket.key() != null ? bucket.key() : "Faixa";
                resultado.put(chave, bucket.docCount());
            }
        }
        return resultado;
    }
}