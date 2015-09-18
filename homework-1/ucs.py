DEBUG = True

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
      
      
      if node in finish:
        return new_path
      #end if child in finish

      if DEBUG: print(str(path[0]) + " = " + " ".join(n.name for n in path[1:]))
      
      explored.add(node)
      
      for child_path in node.cheap_paths():
        child = child_path.end
        
        if DEBUG: print("  -> " + child.name + " = " + str(child_path.cost), end="")
        
        if child in explored:
          if DEBUG: print("\tIn Explored")
          continue
          
        new_path = UCS.new_path(path, child_path)

        for p in frontier:
          if child == p[-1]:
            if new_path[0] < p[0]:
              if DEBUG: print("\tIn Frontier, Replacing")
              frontier = [new_path if p[-1] == child else p for p in frontier]
              continue
            else:
              if DEBUG: print("\tIn Frontier, Continuing")
              continue
          

        if DEBUG: print()

        frontier.append(new_path)
        frontier.sort(key=lambda x: x[0])

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