class Type < ActiveRecord::Base
validates :clickertype,  presence: true, uniqueness: true
end
