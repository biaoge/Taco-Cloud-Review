package tacos.data.jdbc;

import java.sql.Timestamp;
import java.sql.Types;
import java.util.Arrays;
import java.util.Date;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.PreparedStatementCreatorFactory;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Repository;
import org.springframework.jdbc.support.KeyHolder;

import tacos.data.TacoRepository;
import tacos.model.Ingredient;
import tacos.model.Taco;

@Repository
public class JdbcTacoRepository implements TacoRepository {

    private JdbcTemplate jdbc;

    // 只有一个构造函数，不需要 @Autowired
    // 如果你写多个构造函数，就必须用 @Autowired 指明哪一个
    public JdbcTacoRepository(JdbcTemplate jdbc) {
        this.jdbc = jdbc;
    }

    @Override
    public Taco save(Taco taco) {
        long tacoId = saveTacoInfo(taco);
        taco.setId(tacoId);
        for (Ingredient ingredient : taco.getIngredients()) {
            saveIngredientToTaco(ingredient, tacoId);
        }
        return taco;
    }

    // Save the taco info to Taco table and return the generated id
    private long saveTacoInfo(Taco taco) {
        taco.setCreatedAt(new Date());
        // 注意这里是insert into "Taco" 表
        PreparedStatementCreatorFactory pscf = new PreparedStatementCreatorFactory(
                "insert into Taco (name, createdAt) values (?, ?)",
                Types.VARCHAR, Types.TIMESTAMP);
        pscf.setReturnGeneratedKeys(true);
        PreparedStatementCreator psc = pscf
                .newPreparedStatementCreator(
                        Arrays.asList(
                                taco.getName(),
                                new Timestamp(taco.getCreatedAt().getTime())));

        KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbc.update(psc, keyHolder);

        return keyHolder.getKey().longValue();
    }

    // Save the taco's ingredients to the "Taco_Ingredients" join table
    private void saveIngredientToTaco(Ingredient ingredient, long tacoId) {
        // 注意是insert into "Taco_Ingredients"， 这里是update "Taco_Ingredients" 表
        jdbc.update(
                "insert into Taco_Ingredients (taco, ingredient) values (?, ?)",
                tacoId, ingredient.getId());
    }

}
