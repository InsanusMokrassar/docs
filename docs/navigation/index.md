# Navigation

Navigation is a library for simple management for your app views (or some other logics). In this library there are several
important terms:

* `Node` - is a core thing. Node itself contains current config and its state
* `Chain` - some sequence of nodes. In one chain **only the last one** node can be active

## Work explanation

* Only the last (most deep) `node` can be `RESUMED`
* All the `chain`s of resumed `node` will have status `RESUMED`
* Only in the `chain` with status `RESUMED` there are `RESUMED` nodes

??? info Statuses
    There are 3 statuses:
    
    * New - Means that Node/Chain is just created (even before constructor) or has been fully destroyed (in context of navigation)
    * Created - Means that Node/Chain is created or preparing for destroing
    * Started - Means that Node/Chain is hidden and can be resumed/stopped at any time
    * Resumed - Means that Node/Chain now active

    In fact node will retrieve 6 changes of statuses:

    ```mermaid
    flowchart TB
        New -.-> Create
        Create -.-> Created
        Created -.-> Start -.-> Started
        Started --> Resume --> Resumed
        Resumed --> Pause --> Started
        Started --> Stop --> Created
        Created --> Destroy
        Destroy --> New

        DashedLineLegendTitle(Dashed line) -.-> DashedLineLegend(Possible direction before `Created` state)
        SolidLineLegendTitle(Solid line) --> SolidLineLegend(Possible direction after `Created` state)

        class New navigation-new;
        class Destroyed navigation-new;
        class Created navigation-created;
        class Started navigation-started;
        class Resumed navigation-resumed;
    ```
---

### Nodes behaviour

Let's see the next sample:

```mermaid
flowchart LR
    subgraph Nodes/Chains tree
        NodeN1(N1)
        NodeN2(N2)
        
        class NodeN1 navigation-started;
        class NodeN2 navigation-resumed;
    
        subgraph RootChain
            direction LR
            NodeN1 --> NodeN2
        end
    
        class RootChain navigation-resumed;
    end
```

we may say several things about the sample above:

* N2 is the latest node and it is `RESUMED`
* N1 `PAUSED`
* RootChain is `RESUMED`

So, we would like to add new node in the end of stack:

```mermaid
flowchart LR
    subgraph Nodes/Chains tree
        NodeN1(N1)
        NodeN2(N2)
        NodeN3(N3)
        
        class NodeN1 navigation-started;
        class NodeN2 navigation-started;
        class NodeN3 navigation-resumed;
    
        subgraph RootChain
            direction LR
            NodeN1 --> NodeN2
            NodeN2 --> NodeN3
        end
    
        class RootChain navigation-resumed;
    end
```

As we can see, N3 became `RESUMED` and N2 `PAUSED`. Let's try to remove N3:

```mermaid
flowchart LR
    subgraph Nodes/Chains tree
        NodeN1(N1)
        NodeN2(N2)
        
        class NodeN1 navigation-started;
        class NodeN2 navigation-resumed;
    
        subgraph RootChain
            direction LR
            NodeN1 --> NodeN2
        end
    
        class RootChain navigation-resumed;
    end
```

### Chains behaviour

So, let's continue with the sample above. Let's imagine, we need to add new subchain for N2 node.
Whole tree will look like:

```mermaid
flowchart LR
    subgraph Nodes/Chains tree
        direction LR

        NodeN1(N1)
        NodeN2(N2)
        NodeN3(N3)
        
        class NodeN1 navigation-started;
        class NodeN2 navigation-resumed;
        class NodeN3 navigation-resumed;
    
        subgraph RootChain
            direction LR
            NodeN1 --> NodeN2
            NodeN2
        end
    
        NodeN2 --> N2Subchain
    
        subgraph N2Subchain
            direction LR
            NodeN3
        end
    
        class RootChain navigation-resumed;
        class N2Subchain navigation-resumed;
    end
```

Here has been created new N2Subchain with N3 node. Both them resumed because of:

* N2 is resumed. So, N2Subchain supposed to be resumed
* Due to N2Subhain is resumed and N3 is the latest node, it will be resumed too

We may add new subchain to N1:

```mermaid
flowchart LR
    subgraph Nodes/Chains tree
        direction LR
        NodeN1(N1)
        NodeN2(N2)
        NodeN3(N3)
        NodeN4(N4)

        class NodeN1 navigation-started;
        class NodeN2 navigation-resumed;
        class NodeN3 navigation-resumed;
        class NodeN4 navigation-started;

        subgraph RootChain
            direction LR
            NodeN1 --> NodeN2
            NodeN2
        end

        NodeN1 --> N1Subchain
        NodeN2 --> N2Subchain

        subgraph N1Subchain
            direction LR
            NodeN4
        end

        subgraph N2Subchain
            direction LR
            NodeN3
        end

        class RootChain navigation-resumed;

        class N1Subchain navigation-started;
        class N2Subchain navigation-resumed;
    end
```

So, it has been added, but:

* Due to N1 paused state, N1Subchain have inherited it
* Due to N1Subhain is paused, all its nodes paused too

And now we may remove N2 node. This action will trigger next changes:

```mermaid
flowchart LR
    subgraph Changes
        subgraph OldNodesChainsTree [Old Nodes/Chains tree]
            direction TB
            OldNodeN1(N1)
            OldNodeN2(N2)
            OldNodeN3(N3)
            OldNodeN4(N4)
            
            class OldNodeN1 navigation-started;
            class OldNodeN2 navigation-created;
            class OldNodeN3 navigation-created;
            class OldNodeN4 navigation-started;
            
            subgraph OldRootChain [RootChain]
                direction TB
                OldNodeN1 --> OldNodeN2
                OldNodeN2
            end
            
            OldNodeN1 --> OldN1Subchain
            OldNodeN2 --> OldN2Subchain
            
            subgraph OldN1Subchain [N1Subchain]
                direction TB
                OldNodeN4
            end
            
            subgraph OldN2Subchain  [N2Subchain]
                direction TB
                OldNodeN3
            end
            
            class OldRootChain navigation-resumed;
            
            class OldN1Subchain navigation-started;
            class OldN2Subchain navigation-created;
        end
            
        subgraph NewNodesChainsTree [New Nodes/Chains tree]
            direction TB
            NewNodeN1(N1)
            NewNodeN4(N4)
            
            class NewNodeN1 navigation-resumed;
            class NewNodeN4 navigation-resumed;
            
            subgraph NewRootChain [RootChain]
                direction TB
                NewNodeN1
            end
            
            NewNodeN1 ---> NewN1Subchain
            
            subgraph NewN1Subchain [N1Subchain]
                direction TB
                NewNodeN4
            end
            
            class NewRootChain navigation-resumed;
            
            class NewN1Subchain navigation-resumed;
        end

%%        OldNodesChainsTree -.-> NewNodesChainsTree
        OldNodeN1-.->|Become resumed| NewNodeN1
        OldNodeN4-.->|Become resumed| NewNodeN4

    end
```

What has happened:

1. We solved to remove N2 node. Status is changing to stopped
   1. N2Subchain must be stopped
   2. N3 must be stopped as well

!!! tip "Stopping in context of navigation means destroying"

### Large tree sample

```mermaid
flowchart TB

    NodeN1(N1)
    NodeN2(N2)
    NodeN3(N3)
    
    class NodeN1 navigation-started;
    class NodeN2 navigation-started;
    class NodeN3 navigation-resumed;

    subgraph RootChain
        direction TB
        NodeN1-->NodeN2
        NodeN2-->NodeN3
    end

    class RootChain navigation-resumed;

    NodeN4(N4)
    NodeN5(N5)
    NodeN6(N6)
    
    class NodeN4 navigation-started;
    class NodeN5 navigation-started;
    class NodeN6 navigation-started;

    subgraph N2Subchain
        direction TB
        NodeN4-->NodeN5
        NodeN5-->NodeN6
    end

    class N2Subchain navigation-started;
    
    NodeN2 --> N2Subchain

    NodeN7(N7)
    NodeN8(N8)
    
    class NodeN7 navigation-started;
    class NodeN8 navigation-resumed;

    subgraph N3Subchain
        direction TB
        NodeN7 --> NodeN8
    end

    class N3Subchain navigation-resumed;
    
    NodeN3 --> N3Subchain

    NodeN9(N9)
    NodeN10(N10)
    
    class NodeN9 navigation-started;
    class NodeN10 navigation-resumed;

    subgraph N3Subchain2
        direction TB
        NodeN9 --> NodeN10
    end

    class N3Subchain2 navigation-resumed;
    
    NodeN3 --> N3Subchain2
```
