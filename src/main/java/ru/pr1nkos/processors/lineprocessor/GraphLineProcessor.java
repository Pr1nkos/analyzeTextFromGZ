package ru.pr1nkos.processors.lineprocessor;

import org.jgrapht.Graph;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.SimpleGraph;
import org.jgrapht.traverse.BreadthFirstIterator;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 * The type Graph line processor.
 */
public class GraphLineProcessor implements LineProcessor {
    /**
     * The Graph.
     */
    protected final Graph<String, DefaultEdge> graph = new SimpleGraph<>(DefaultEdge.class);
    /**
     * The Groups.
     */
    protected final Map<String, Set<String>> groups = new ConcurrentHashMap<>();

    @Override
    public void processLine(String line) {
        String[] columns = line.split(";");

        Set<String> relatedNodes = getRelatedNodes(columns);

        if (relatedNodes.isEmpty()) {
            handleNewLine(line);
        } else {
            handleExistingGroup(line, relatedNodes);
        }
    }

    private Set<String> getRelatedNodes(String[] columns) {
        Set<String> relatedNodes = new HashSet<>();
        for (String value : columns) {
            String sanitizedValue = sanitizeValue(value);
            if (graph.containsVertex(sanitizedValue)) {
                relatedNodes.add(sanitizedValue);
            }
        }
        return relatedNodes;
    }

    private String sanitizeValue(String value) {
        return value.replace("\"", "").trim();
    }

    private void handleNewLine(String line) {
        graph.addVertex(line);
        groups.put(line, Collections.singleton(line));
    }

    private void handleExistingGroup(String line, Set<String> relatedNodes) {
        Set<String> connectedGroup = new HashSet<>();
        for (String node : relatedNodes) {
            connectedGroup.addAll(getConnectedComponent(node));
        }

        connectedGroup.add(line);
        addGroupToGraph(connectedGroup);

        // Update the group map
        for (String node : connectedGroup) {
            groups.put(node, connectedGroup);
        }
    }

    private void addGroupToGraph(Set<String> connectedGroup) {
        for (String node : connectedGroup) {
            graph.addVertex(node);
        }

        for (String node : connectedGroup) {
            for (String other : connectedGroup) {
                if (!node.equals(other) && !graph.containsEdge(node, other)) {
                    graph.addEdge(node, other);
                }
            }
        }
    }

    /**
     * Gets connected component.
     *
     * @param node the node
     * @return the connected component
     */
    protected Set<String> getConnectedComponent(String node) {
        Set<String> component = new HashSet<>();
        BreadthFirstIterator<String, DefaultEdge> iterator = new BreadthFirstIterator<>(graph, node);

        while (iterator.hasNext()) {
            component.add(iterator.next());
        }

        return component;
    }

    @Override
    public List<Set<String>> getGroups() {
        return new ArrayList<>(new HashSet<>(groups.values()));
    }
}