package org.example;

public class ResourceFactory {
    public static Resource createResource(ResourceType type, int quantity) {
        return new Resource(type, quantity);
    }
}

