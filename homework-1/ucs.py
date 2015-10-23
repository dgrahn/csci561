DEBUG = False

"""
An implementation of the UCS algorithm.
"""
class UCS:

  @staticmethod
  def run(time, start, finish):

    # Check to make sure we aren't at the solution
    if start in finish:
      return [time, start]

    # Setup the variables
    explored = set()
    frontier = [[time, start]]
    
    while frontier:
    
      # We are storing paths, so get the node too
      path = frontier.pop(0)
      node = path[-1]
      time = path[0] % 24
      
      if DEBUG: print("Exploring " + str(path[0]) + " = " + " ".join(n.name for n in path[1:]))
      
      if node in finish:
        if DEBUG: print("\tSolution")
        path[0] %= 24
        return path
      #end if child in finish
      
      explored.add(node)
      
      for child_path in node.cheap_paths(time):
        child = child_path.end
        
        if DEBUG: print("  -> " + child.name + " = " + str(child_path.cost), end="")
        
        if child in explored:
          if DEBUG: print("\tIn Explored")
          continue
          
        new_path = UCS.new_path(path, child_path)
        
        inFrontier = False

        for p in frontier:
          if child == p[-1]:
            if new_path[0] < p[0]:
              if DEBUG: print("\tIn Frontier, Replacing")
              frontier = [new_path if p[-1] == child else p for p in frontier]
              inFrontier = True
              continue
            else:
              if DEBUG: print("\tIn Frontier, Continuing")
              inFrontier = True
              continue
          

        if DEBUG: print()

        if not inFrontier:
          frontier.append(new_path)
        frontier.sort(key=lambda x: (x[0], x[-1].name))

    #end while frontier
      
    # Failure
    if DEBUG: print("Failure!")
    return None

  #end run
  
  @staticmethod
  def new_path(path, child_path):
    new_path = list(path)
    new_path[0] += child_path.cost
    new_path.append(child_path.end)
    return new_path
  #end new_path

#end BFS