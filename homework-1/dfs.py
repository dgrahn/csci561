DEBUG = False

"""
An implementation of the DFS algorithm.
"""
class DFS:
  explored = []

  @staticmethod
  def run(start, finish, depth = 0):
  
    if depth == 0:
      DFS.explored = []
      
    DFS.explored.append(start)

    if DEBUG: print("DFS exploring " + start.name)

    # Check to make sure we aren't at the solution
    if start in finish:
      return [start]
    
    for child in start.children():
      if DEBUG: print("  -> " + child.name, end="")
      
      if child in DFS.explored: continue
      if child in finish: return [start, child]
      
      results = DFS.run(child, finish, depth + 1)
      if results is not None:
        return DFS.new_path(results, start)

    #end while frontier
      
    # Failure
    return None

  #end run
  
  @staticmethod
  def new_path(path, child):
    new_path = list(path)
    new_path.insert(0, child)
    return new_path
  #end new_path

#end BFS