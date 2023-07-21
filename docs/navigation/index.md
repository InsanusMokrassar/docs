# Navigation

Navigation is a library for simple management for your app views (or some other logics). In this library there are several
important terms:

* `Node` - is a core thing. Node itself contains current config and its state
* `Chain` - some sequence of nodes. In one chain **only the last one** node can be active

## Nodes tree

Let's see the next sample:

```mermaid
flowchart TB

    NodeN1(N1)
    NodeN2(N2)
    NodeN3(N3)
    
    class NodeN1 navigation-paused;
    class NodeN1 navigation-part;
    class NodeN2 navigation-paused;
    class NodeN2 navigation-part;
    class NodeN3 navigation-resumed;
    class NodeN3 navigation-part;

    subgraph RootChain
        direction TB
        NodeN1 --> NodeN2
        NodeN2 --> NodeN3
    end

    class RootChain navigation-resumed;
    class RootChain navigation-part;

    NodeN4(N4)
    NodeN5(N5)
    NodeN6(N6)
    
    class NodeN4 navigation-paused;
    class NodeN4 navigation-part;
    class NodeN5 navigation-paused;
    class NodeN5 navigation-part;
    class NodeN6 navigation-paused;
    class NodeN6 navigation-part;

    subgraph N2Subchain
        direction TB
        NodeN4 --> NodeN5
        NodeN5 --> NodeN6
    end

    class N2Subchain navigation-paused;
    class N2Subchain navigation-part;
    
    NodeN2 --> N2Subchain

    NodeN7(N7)
    NodeN8(N8)
    
    class NodeN7 navigation-paused;
    class NodeN7 navigation-part;
    class NodeN8 navigation-resumed;
    class NodeN8 navigation-part;

    subgraph N3Subchain
        direction TB
        NodeN7 --> NodeN8
    end

    class N3Subchain navigation-resumed;
    class N3Subchain navigation-part;
    
    NodeN3 --> N3Subchain

    NodeN9(N9)
    NodeN10(N10)
    
    class NodeN9 navigation-paused;
    class NodeN9 navigation-part;
    class NodeN10 navigation-resumed;
    class NodeN10 navigation-part;

    subgraph N3Subchain2
        direction TB
        NodeN9 --> NodeN10
    end

    class N3Subchain2 navigation-resumed;
    class N3Subchain2 navigation-part;
    
    NodeN3 --> N3Subchain2
```

Any hierarchy starts with some root chain.
