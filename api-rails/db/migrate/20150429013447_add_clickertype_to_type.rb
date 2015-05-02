class AddClickertypeToType < ActiveRecord::Migration
  def change
    add_column :types, :clickertype, :int
  end
end
