# Navigation

Navigation is a library for simple management for your app views (or some other logics). In this library there are several
important terms:

* `Node` - is a core thing. Node itself contains current config and its state
* `Chain` - some sequence of nodes. In one chain **only the last one** node can be active

## Nodes tree

Let's see the next sample:

```mermaid
flowchart BT

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
        NodeN1 --> NodeN2
        NodeN2 --> NodeN3
    end
    class RootChain navigation-resumed;
    class RootChain navigation-part;
```

Any hierarchy starts with some root chain.
