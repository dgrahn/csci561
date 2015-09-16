DEBUG = False

"""
An implementation of the BFS algorithm.
"""
class BFS:

  @staticmethod
  def run(start, finish):

    # Check to make sure we aren't at the solution
    if start in finish:
      return [start]

    # Setup the variables
    explored = set()
    frontier = [[start]]
    
    while frontier:
    
      # We are storing paths, so get the node too
      path = frontier.pop(0)
      node = path[-1]

      if DEBUG: print(" ".join(n.name for n in path))
      
      explored.add(node)
      
      for child in node.children():
        if DEBUG: print("  -> " + child.name, end="")
        
        if child in explored:
          if DEBUG: print("\tIn Explored")
          continue
          
        if child in (p[-1] for p in frontier):
          if DEBUG: print("\tIn Frontier")
          continue
          
        new_path = BFS.new_path(path, child)

        if DEBUG: print()

        if child in finish:
          return new_path
        #end if child in finish

        frontier.append(new_path)

    #end while frontier
      
    # Failure
    if DEBUG: print("Failure!")
    return None

  #end run
  
  @staticmethod
  def new_path(path, child):
    new_path = list(path)
    new_path.append(child)
    return new_path
  #end new_path

#end BFS