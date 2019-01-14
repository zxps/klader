package org.rubio.klader.core.command.system;

import org.rubio.klader.core.command.types.Command;
import org.rubio.klader.core.command.types.CommandContext;
import org.rubio.klader.core.console.parser.QueryParser;
import org.rubio.klader.core.console.parser.QueryReader;
import org.rubio.klader.core.storage.manager.RegistryManager;
import org.rubio.klader.core.storage.repository.Criteria;
import org.rubio.klader.core.storage.repository.Repository;
import org.rubio.klader.core.storage.repository.matcher.ExpressionCriteriaMatcher;

import java.io.PrintStream;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class QueryCommand implements Command {

    @Override
    public void handle(RegistryManager manager, CommandContext context) {
        PrintStream out = (PrintStream) context.getOutput();
        QueryParser parser = new QueryParser(context.getOptions());
        QueryReader reader = parser.parse();
        Repository repository;

        if (reader.getDescribeModel() != null) {
            repository = manager.getRepository(reader.getDescribeModel());
            List<String> modelFields = repository.getModelFields();

            out.println("Model: " + reader.getDescribeModel());
            for(String field: modelFields) {
                out.println(field);
            }
            return;
        }

        repository = manager.getRepository(reader.getModel());

        Criteria criteria = new Criteria();
        if (null != reader.getWhereSyntaxTree()) {
            criteria.setMatcher(new ExpressionCriteriaMatcher(reader.getWhereSyntaxTree()));
        }

        if (reader.getSelectFields().contains("COUNT")) {
            Map<String, String> map = repository.execute(criteria);
            for(String key: map.keySet()) {
                out.println(key + ": " + map.get(key));
            }
            return;
        }

        criteria.setOffset(reader.getOffset());
        criteria.setMaxResults(reader.getLimit());
        criteria.setSort(reader.getSortMap());

        List entities = repository.search(criteria);
        String delim = reader.getSelectDelimiter();
        Iterator iterator = entities.iterator();

        while (iterator.hasNext()) {
            Object entity = iterator.next();
            List<String> buffer = reader.readFields(entity);
            out.println(String.join(delim, buffer));
        }
    }

    @Override
    public String getIdentifier() {
        return "q";
    }

    @Override
    public String getName() {
        return "Execute query";
    }

    @Override
    public String getDescription() {
        return null;
    }
}
